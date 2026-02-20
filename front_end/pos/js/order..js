const CUSTOMER_URL = "http://localhost:8080/app/v1/customer";
    const ITEM_URL = "http://localhost:8080/app/v1/item";
    const ORDER_URL = "http://localhost:8080/app/v1/order";

    let customers = [];
    let items = [];
    let cart = [];

    function showError(msg) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: msg,
            background: '#1e1e1e',
                                color: '#ffffff',
                                iconColor: '#a5dc86'
        });
    }

    function showSuccess(msg) {
        Swal.fire({
            icon: "success",
            title: "Success",
            text: msg,
            timer: 1500,
            showConfirmButton: false,
            background: '#1e1e1e',
                                color: '#ffffff',
                                iconColor: '#a5dc86'
        });
    }

    function money(n) { return Number(n || 0).toFixed(2); }

    function clearItemInputs() {
        $("#itemName").val("");
        $("#unitPrice").val("");
        $("#availableQty").val("");
        $("#orderQty").val("");
    }

    function renderCart() {
        $("#cartBody").empty();

        cart.forEach((c, idx) => {
            $("#cartBody").append(`
        <tr>
          <td>${c.itemId}</td>
          <td>${c.name}</td>
          <td>${c.qty}</td>
          <td>${money(c.unitPrice)}</td>
          <td>${money(c.total)}</td>
          <td><button class="btn btn-sm btn-danger btnRemove" data-index="${idx}">Remove</button></td>
        </tr>
      `);
        });

        const grandTotal = cart.reduce((sum, x) => sum + Number(x.total), 0);
        $("#grandTotal").text(money(grandTotal));
    }

    function loadNextOrderId() {
        $.ajax({
            url: ORDER_URL + "/next-id",
            method: "GET",
            success: function (id) {
                $("#orderId").val(id);
            },
            error: function () {
                $("#orderId").val("Auto");
            }
        });
    }

    function loadCustomers() {
        $.ajax({
            url: CUSTOMER_URL,
            method: "GET",
            dataType: "json",
            success: function (resp) {
                customers = resp?.data || [];

                $("#cusSelect").empty().append(`<option value="">-- Select Customer --</option>`);
                customers.forEach((c) => {
                    $("#cusSelect").append(`<option value="${c.cId}">${c.cId}</option>`);
                });
            },
            error: function (xhr) {
                console.log(xhr.responseText);
                showError("Customer load failed: " + (xhr.responseText || xhr.status));
            }
        });
    }

    function loadItems() {
        $.ajax({
            url: ITEM_URL,
            method: "GET",
            dataType: "json",
            success: function (resp) {
                items = resp?.data || [];

                $("#itemSelect").empty().append(`<option value="">-- Select Item --</option>`);
                items.forEach((i) => {
                    $("#itemSelect").append(`<option value="${i.id}">${i.id}</option>`);
                });
            },
            error: function (xhr) {
                console.log(xhr.responseText);
                showError("Item load failed: " + (xhr.responseText || xhr.status));
            }
        });
    }


    $("#cusSelect").on("change", function () {
        const cid = Number($(this).val());
        const cus = customers.find((c) => c.cId === cid);
        $("#cusNameView").text(cus ? cus.cName : "-");
    });

    $("#itemSelect").on("change", function () {
        const itemId = Number($(this).val());
        const it = items.find((i) => i.id === itemId);

        if (!it) {
            clearItemInputs();
            return;
        }

        $("#itemName").val(it.name);
        $("#unitPrice").val(money(it.price));
        $("#availableQty").val(it.qty);
        $("#orderQty").val(1).focus();
    });

    $("#btnAddToCart").on("click", function () {
        const customerId = Number($("#cusSelect").val());
        const itemId = Number($("#itemSelect").val());
        const qty = Number($("#orderQty").val());

        if (!customerId) return showError("Select a customer!");
        if (!itemId) return showError("Select an item!");
        if (!qty || qty <= 0) return showError("Enter valid order qty!");

        const it = items.find((i) => i.id === itemId);
        if (!it) return showError("Invalid item!");

        const available = Number(it.qty);
        if (qty > available) return showError(`Not enough stock! Available: ${available}`);

        const existing = cart.find((c) => c.itemId === itemId);
        const newCartQty = existing ? existing.qty + qty : qty;

        if (newCartQty > available) return showError(`Cart qty exceeds available stock! Available: ${available}`);

        if (existing) {
            existing.qty = newCartQty;
            existing.total = existing.qty * existing.unitPrice;
        } else {
            cart.push({
                itemId: it.id,
                name: it.name,
                qty: qty,
                unitPrice: Number(it.price),
                total: qty * Number(it.price)
            });
        }

        renderCart();
        $("#orderQty").val(1);
    });

    $("#cartBody").on("click", ".btnRemove", function () {
        const idx = Number($(this).data("index"));
        cart.splice(idx, 1);
        renderCart();
    });

    $("#btnPlaceOrder").on("click", function () {
        const customerId = Number($("#cusSelect").val());
        if (!customerId) return showError("Select a customer!");
        if (cart.length === 0) return showError("Cart is empty!");

        const orderPayload = {
            customerId: customerId,
            items: cart.map((c) => ({
                itemId: c.itemId,
                qty: c.qty,
                unitPrice: c.unitPrice
            })),
            total: Number(cart.reduce((sum, x) => sum + Number(x.total), 0))
        };

        $.ajax({
            url: ORDER_URL,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(orderPayload),
            success: function () {
                showSuccess("Order placed");

                cart = [];
                renderCart();
                $("#cusSelect").val("");
                $("#cusNameView").text("-");
                $("#itemSelect").val("");
                clearItemInputs();

                loadItems();
                loadNextOrderId();
            },
            error: function (xhr) {
                console.log(xhr.responseText);
                showError("Place order failed: " + (xhr.responseText || xhr.status));
            }
        });
    });

    $(document).ready(function () {
        loadCustomers();
        loadItems();
        renderCart();
        loadNextOrderId();
    });