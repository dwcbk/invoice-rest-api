<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Invoices - REST Endpoints</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>
<h1>Invoices</h1>
<p>Search invoices:</p>
<form action="/v1/invoice">
    <input name="invoice_number" placeholder="Invoice Number" />
    <input name="po_number" placeholder="PO Number" />
    <input type="submit" value="Search" />
</form>

<p>Add invoices:</p>
<form action="/v1/invoice" name="submitInvoiceForm" id="submitInvoiceForm">
    <input name="invoice_number" placeholder="Invoice Number" />
    <input name="po_number" placeholder="PO Number" />
    <input name="due_date" placeholder="Due Date" />
    <input name="amount_cents" placeholder="Amount (cents)" type="number" />
    <input id="submitInvoiceBtn" type="submit" value="Save" />
</form>
<p id="responseTitle" style="display:none;"><strong>Invoice Added:</strong></p>
<pre id="addInvoiceResult"></pre>

<script>
    function convertFormToJSON(array){
        var json = {};
        $.each(array, function() {
            console.log(this.name + "=" + this.value);
            json[this.name] = this.value || '';
        });
        console.log("JSON: ", json);
        return json;
    }

    $(document).ready(function(){

        $('form[name=submitInvoiceForm]').submit(function(event){
            event.preventDefault();
            var data = $("#submitInvoiceForm").serializeArray();
            var jsonData = convertFormToJSON(data);
            console.log("Submitting form via AJAX");
            console.log("data: ", data);
            console.log("jsonData: ", jsonData);
            $("#addInvoiceResult").html(jsonData);
            $.ajax({
                url: '/v1/invoice',
                type : "POST",
                data : jsonData,
                success : function(data) {
                    $("#responseTitle").show();
                    $("#responseTitle").html("Invoice Added:");
                    $("#responseTitle").css("color", "black");
                    $("#addInvoiceResult").text(JSON.stringify(data, null, 2));
                },
                error: function(xhr, resp, text) {
                    $("#responseTitle").show();
                    $("#responseTitle").html("Bad request");
                    $("#responseTitle").css("color", "red");
                    console.log(xhr, resp, text);
                }
            })
        });
    });

</script>
</body>
</html>