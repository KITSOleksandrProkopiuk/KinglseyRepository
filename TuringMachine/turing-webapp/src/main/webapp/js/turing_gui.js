$(document).ready(function() {
	// $("#dialog:ui-dialog").dialog("destroy");
		$("#add_row_dialog").dialog(
				{
					width : 500,
					autoOpen : false,
					modal : true,
					buttons : {
						Cancel : function() {
							$(this).dialog("close");
						},
						Save : function() {
							var state_name = $("#state_name").attr("value");
							var to_read = $("#to_read").attr("value");
							var to_write = $("#to_write").attr("value");
							var direction = $("#direction").attr("value");
							var next_state = $("#next_state").attr("value");

							addStateRow(state_name, to_read, to_write,
									direction, next_state);

						}
					},
					close : function() {

					},
					open : function() {

					}
				});

		$("#button_add_row").click(function() {
			$("#add_row_dialog").dialog('open');
		});

		function addStateRow(state_name, to_read, to_write, direction,
				next_state) {

			var table = document.getElementById("state_table");
			var row = table.insertRow(table.rows.length);// for Crome, IE

			var cell_state_name = row.insertCell(0);
			var element1 = document.createElement("input");
			element1.type = "text";
			element1.value = state_name;
			element1.readonly="readonly";
				elementname =table.rows.length+  "_startName"; 
			element1.name = elementname;
			
			
			cell_state_name.appendChild(element1);

			var cell_to_read = row.insertCell(1);
			var element2 = document.createElement("input");
			element2.type = "text";
			element2.value = to_read;
			element2.readonly="readonly";
			elementname = table.rows.length+"_to_read" ; 
			element2.name = elementname;
			cell_to_read.appendChild(element2);

			var cell_to_write = row.insertCell(2);
			var element3 = document.createElement("input");
			element3.type = "text";
			element3.value = to_write ;
			element3.readonly="readonly";
			elementname = table.rows.length+"_to_write"; 
			element3.name = elementname;
			cell_to_write.appendChild(element3);
			
			var cell_direction = row.insertCell(3);
			var element4 = document.createElement("input");
			element4.type = "text";
			element4.value = direction;
			element4.readonly="readonly";
			elementname = table.rows.length+"_direction"; 
			element4.name = elementname;
			cell_direction.appendChild(element4);
			
			var cell_next_state = row.insertCell(4);
			var element5 = document.createElement("input");
			element5.type = "text";
			element5.value = next_state;
			element5.readonly="readonly";
			elementname =table.rows.length + "_next_state" ; 
			element5.name = elementname;
			cell_next_state.appendChild(element5);		

		}

	});