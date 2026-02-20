const BASE_URL = "http://localhost:8080/app/v1/customer";

    function clearForm() {
        $("#cusId").val("");
        $("#cusName").val("");
        $("#cusAddress").val("");
        $("#cusId").focus();
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

    function loadCustomers() {
        $.ajax({
            url: BASE_URL,
            method: "GET",
            dataType: "json",
            success: function (resp) {
                $("#tblCustomers").empty();

                const customers = resp?.data || [];
                customers.forEach((c) => {
                    $("#tblCustomers").append(`
            <tr>
              <td>${c.cId ?? ""}</td>
              <td>${c.cName ?? ""}</td>
              <td>${c.cAddress ?? ""}</td>
            </tr>
          `);
                });
            },
            error: function (xhr) {
                const resp = xhr.responseJSON;
                showError(resp?.message || xhr.responseText || "Failed to load customers");
            }
        });
    }

    $("#tblCustomers").on("click", "tr", function () {
        $("#cusId").val($(this).children().eq(0).text().trim());
        $("#cusName").val($(this).children().eq(1).text().trim());
        $("#cusAddress").val($(this).children().eq(2).text().trim());
    });


    $("#btnAdd").on("click", function () {
        const customer = {
            cName: $("#cusName").val().trim(),
            cAddress: $("#cusAddress").val().trim()
        };

        $.ajax({
            url: BASE_URL,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(customer),

            success: function (resp) {
                const code = resp?.code ?? resp?.status;
                if (code === 201 || code === 200) {
                    showSuccess(resp.message || "Customer saved");
                    clearForm();
                    loadCustomers();
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


    $("#btnUpdate").on("click", function () {
        const customer = {
            cId: Number($("#cusId").val().trim()),
            cName: $("#cusName").val().trim(),
            cAddress: $("#cusAddress").val().trim()
        };

        if (!customer.cId || Number.isNaN(customer.cId)) {
            showError("Select a customer first (valid ID required)");
            return;
        }

        $.ajax({
            url: BASE_URL,
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(customer),

            success: function (resp) {
                const code = resp?.code ?? resp?.status;
                if (code === 200 || code === 201) {
                    showSuccess(resp.message || "Customer updated");
                    clearForm();
                    loadCustomers();
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


    $("#btnDelete").on("click", function () {
        const id = $("#cusId").val().trim();

        if (!id) {
            showError("Enter/select a customer ID first!");
            return;
        }

        if (!confirm(`Delete customer ${id}?`)) return;

        $.ajax({
            url: `${BASE_URL}/${encodeURIComponent(id)}`,
            method: "DELETE",

            success: function (resp) {
                const code = resp?.code ?? resp?.status;
                if (code === 200 || code === 201) {
                    showSuccess(resp.message || "Customer deleted");
                    clearForm();
                    loadCustomers();
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

    $("#btnClear").on("click", clearForm);

    $(document).ready(function () {
        loadCustomers();
    });