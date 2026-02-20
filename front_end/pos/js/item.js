const BASE_URL = "http://localhost:8080/app/v1/item";

    function clearForm() {
        $("#itemId").val("");
        $("#itemName").val("");
        $("#itemQty").val("");
        $("#itemPrice").val("");
        $("#itemId").focus();
    }

    function showSuccess(message) {
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: message,
            timer: 1800,
            showConfirmButton: false,
            background: '#1e1e1e',
                    color: '#ffffff',
                    iconColor: '#a5dc86'
        });
    }

    function showError(message) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: message,
            background: '#1e1e1e',
                                color: '#ffffff',
                                iconColor: '#a5dc86'
        });
    }

    function loadItems() {
        $.ajax({
            url: BASE_URL,
            method: "GET",
            dataType: "json",
            success: function (resp) {
                $("#itemTable").empty();

                const items = resp?.data || [];

                items.forEach((i) => {
                    $("#itemTable").append(`
                        <tr>
                          <td>${i.id ?? ""}</td>
                          <td>${i.name ?? ""}</td>
                          <td>${i.qty ?? ""}</td>
                          <td>${i.price ?? ""}</td>
                        </tr>
                    `);
                });
            },
            error: function (xhr) {
                const resp = xhr.responseJSON;
                showError(resp?.message || xhr.responseText || "Failed to load items");
            }
        });
    }

    $("#itemTable").on("click", "tr", function () {
        $("#itemId").val($(this).children().eq(0).text().trim());
        $("#itemName").val($(this).children().eq(1).text().trim());
        $("#itemQty").val($(this).children().eq(2).text().trim());
        $("#itemPrice").val($(this).children().eq(3).text().trim());
    });


    $("#addBtn").on("click", function () {

        const item = {
            name: $("#itemName").val().trim(),
            qty: Number($("#itemQty").val()),
            price: Number($("#itemPrice").val())
        };

        $.ajax({
            url: BASE_URL,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(item),

            success: function (resp) {
                const code = resp?.code ?? resp?.status;
                if (code === 201 || code === 200) {
                    showSuccess(resp.message || "Item saved");
                    clearForm();
                    loadItems();
                } else {
                    showError(resp.message || "Something went wrong");
                }
            },

            error: function (xhr) {
                const resp = xhr.responseJSON;

                if (resp?.data && typeof resp.data === "object") {
                    const msg = Object.entries(resp.data)
                        .map(([k, v]) => `${k}: ${v}`)
                        .join("\n");
                    showError(msg);
                } else {
                    showError(resp?.message || xhr.responseText || "Bad Request (400)");
                }
            }
        });
    });


    $("#updateBtn").on("click", function () {

        const item = {
            id: Number($("#itemId").val().trim()),
            name: $("#itemName").val().trim(),
            qty: Number($("#itemQty").val()),
            price: Number($("#itemPrice").val())
        };

        if (!item.id || Number.isNaN(item.id)) {
            showError("Select an item first (valid ID required)");
            return;
        }

        $.ajax({
            url: BASE_URL,
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(item),
            success: function (resp) {
                showSuccess(resp.message || "Item updated");
                clearForm();
                loadItems();
            },
            error: function (xhr) {
                showError(xhr.responseJSON?.message || xhr.responseText || "Update failed");
            }
        });
    });



    $("#deleteBtn").on("click", function () {

        const id = $("#itemId").val().trim();

        if (!id) {
            showError("Enter/select an item ID first!");
            return;
        }

        if (!confirm(`Delete item ${id}?`)) return;

        $.ajax({
            url: `${BASE_URL}/${encodeURIComponent(id)}`,
            method: "DELETE",

            success: function (resp) {
                const code = resp?.code ?? resp?.status;
                if (code === 200 || code === 201) {
                    showSuccess(resp.message || "Item deleted");
                    clearForm();
                    loadItems();
                } else {
                    showError(resp.message || "Something went wrong");
                }
            },

            error: function (xhr) {
                const resp = xhr.responseJSON;

                if (resp?.data && typeof resp.data === "object") {
                    const msg = Object.entries(resp.data)
                        .map(([k, v]) => `${k}: ${v}`)
                        .join("\n");
                    showError(msg);
                } else {
                    showError(resp?.message || xhr.responseText || "Request failed");
                }
            }
        });
    });

    $("#clearBtn").on("click", clearForm);

    $(document).ready(function () {
        loadItems();
    });