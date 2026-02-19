const ORDER_BASE_URL = "http://localhost:8080/api/v1/order";

// 1. PLACE ORDER (Save)
function placeOrder() {
    let orderId = $('#oId').val();
    let customerId = $('#cId').val();
    let itemCode = $('#iCode').val();
    let qty = $('#oQty').val();

    console.log("Placing order:", orderId, customerId, itemCode, qty);

    // Usually, you send an object that matches your OrderDTO
    $.ajax({
        url: ORDER_BASE_URL,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "orderId": orderId,
            "customerId": customerId,
            "orderDate": itemCode,
            "total": parseInt(qty)
        }),
        success: function (res) {
            alert("Order Placed Successfully!");
            getAllOrders();
            clearOrderFields();
        },
        error: function (error) {
            alert("Error placing order. Check if IDs exist in DB.");
            console.error(error);
        }
    });
}

// 2. GET ALL ORDERS
function getAllOrders() {
    $.ajax({
        url: ORDER_BASE_URL,
        method: "GET",
        success: function (res) {
            console.log("Orders received:", res);
            $("#orderTable tbody").empty();

            res.forEach(order => {
                let row = `<tr>
                    <td>${order.orderId}</td>
                    <td>${order.customerId}</td>
                    <td>${order.itemCode}</td>
                    <td>${order.qty}</td>
                </tr>`;
                $("#orderTable tbody").append(row);
            });
        },
        error: function (error) {
            console.error("AJAX Error:", error);
            alert("Check if your Backend is running on port 8080");
        }
    });
}
