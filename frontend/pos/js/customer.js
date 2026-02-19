const BASE_URL = "http://localhost:8080/api/v1/customers";

function saveCustomer() {
    let cId=$('#customerId').val();
    let cName=$('#customerName').val();
    let cAddress=$('#customerAddress').val();

    console.log(cId,cName,cAddress)

    $.ajax({
        url: BASE_URL,
        method:"POST",
        contentType:"application/json",
        "data": JSON.stringify({
            "name": cName,
            "address": cAddress
        }),
        success:function (res) {
            alert("done")
        },
        error:function (error) {
            alert("network error")
        }
    })
    }

function updateCustomer() {
    let cId=$('#customerId').val();
    let cName=$('#customerName').val();
    let cAddress=$('#customerAddress').val();

    $.ajax({
        url: BASE_URL,
        method:"PUT",
        contentType:"application/json",
        "data": JSON.stringify({
            "id": cId,
            "name": cName,
            "address": cAddress
        }),
        success:function (res) {
            alert("done")
        },
        error:function (error) {
            alert("network error")
        }
    })
    }



function getAllCustomers() {
    $.ajax({
        url: BASE_URL,
        method: "GET",
        success: function (res) {
            console.log("Data received:", res);
            $("#customerTable tbody").empty();

            res.forEach(customer => {
                let row = `<tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.address}</td>
                </tr>`;

                $("#customerTable tbody").append(row);
            });
        },
        error: function (error) {
            console.error("AJAX Error:", error);
            alert("Check if your Backend is running on port 8080");
        }
    });
}

function deleteCustomer() {
    let id = $("#customerId").val();

    if (!id) {
        alert("Please enter a Customer Id to delete");
        return;
    }
    $.ajax({
        url: BASE_URL + "?customerId=" + id,
        method: "DELETE",example
        success: function (res) {
            alert("Customer Deleted!");
            getAllCustomers();
            clearFields();
        },
        error: function (error) {
            alert("Check console for error details");
            console.error("Delete Error:", error);
        }
    });
}