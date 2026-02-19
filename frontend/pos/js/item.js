const ITEM_BASE_URL = "http://localhost:8080/api/v1/item";

function saveItem() {
    let code = $('#itemCode').val();
    let name = $('#itemName').val();
    let price = $('#itemPrice').val();
    let qty = $('#itemQty').val();

    console.log(code, name, price, qty);

    $.ajax({
        url: ITEM_BASE_URL,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "iCode": code,
            "iName": name,
            "iPrice": price,
            "iQty": qty
        }),
        success: function (res) {
            alert("Item Saved Successfully!");
            getAllItem();
        },
        error: function (error) {
            alert("Network Error: Could not save item");
        }
    });
}

function updateItem() {
    let code = $('#itemCode').val();
    let name = $('#itemName').val();
    let price = $('#itemPrice').val();
    let qty = $('#itemQty').val();

    $.ajax({
        url: ITEM_BASE_URL,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            "iCode": code,
            "iName": name,
            "iPrice": price,
            "iQty": qty
        }),
        success: function (res) {
            alert("Item Updated Successfully!");
            getAllItem();
            clearItemFields();
        },
        error: function (error) {
            alert("Network Error: Could not update item");
        }
    });
}

function getAllItem() {
    $.ajax({
        url: ITEM_BASE_URL,
        method: "GET",
        success: function (res) {
            console.log("Items received:", res);
            $("#itemTable tbody").empty();

            res.forEach(item => {
                let row = `<tr>
                    <td>${item.iCode}</td>
                    <td>${item.iName}</td>
                    <td>${item.iPrice}</td>
                    <td>${item.iQty}</td>
                </tr>`;
                $("#itemTable tbody").append(row);
            });
        },
        error: function (error) {
            console.error("AJAX Error:", error);
            alert("Check if your Backend is running on port 8080");
        }
    });
}

function deleteItem() {
    let code = $("#itemCode").val();

    if (!code) {
        alert("Please enter an Item Code to delete");
        return;
    }

    $.ajax({
        url: ITEM_BASE_URL + "?itemCode=" + code,
        method: "DELETE",
        success: function (res) {
            alert("Item Deleted!");
            getAllItem();
            clearItemFields();
        },
        error: function (error) {
            alert("Delete Error: Check console for details");
            console.error("Delete Error:", error);
        }
    });
}