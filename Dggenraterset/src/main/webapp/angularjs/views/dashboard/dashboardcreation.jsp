<!DOCTYPE html>
<html>
<head>
<style type="text/css">

table { table-layout:fixed; } td{ overflow:hidden; text-overflow: ellipsis; }
</style>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <script type="text/javascript">
   var sum=0;
   var mainflag=1;
   var total=0;
   var analogdata=new Array();
   var digitaldata=new Array();
   var rs232data=new Array();

   function getdata()
   {
	
	   callGETJSONAjax("admin/Dashboard",getdashboarddata);
	   callGETJSONAjax("admin/deviceinfolist",getjsondata);
	  /*  callGETJSONAjax("admin/parameters/Analog",getanalogdata);
	   callGETJSONAjax("admin/parameters/Digital",getDigitaldata);
	   callGETJSONAjax("admin/parameters/RS232",getRS232data); */
   }
   
   function getjsondata(msg2)
   {
     	var  json= eval(msg2);
           var conten="";
   		var select = '<select name="deviceidss" id="deviceidss"  class="chosen-select"  style="width: 100%;" onchange="setprofile()" ><option value="">Please Select</option>';
   		for (var i = 0; i < json.length; i++) 
   		{
   			conten = conten+ ' <option  value="'+json[i].deviceinfo_id+'">'+ json[i].devicename + '</option>';
   		}
   		$("#devicelist").html(select + conten + "</select>");
   }
   function getdashboarddata(msg2)
   {
		var  json= eval(msg2);
	   var conten="";
		var select = '<select name="dashboard" id="dashboard"  class="chosen-select"  style="width: 100%;" ><option value="">Please Select</option>';
		for (var i = 0; i < json.length; i++) 
		{
			conten = conten+ ' <option  value="'+json[i].id+'">'+ json[i].dashboardname + '</option>';
		}
		$("#dashboardlist").html(select + conten + "</select>");
   }
   
   function setprofile()
   {
	   var deviceid=$("#deviceidss").val();
	  // alert(deviceid);
	   callGETJSONAjax("admin/deviceinfolist/"+deviceid,getdevicejsondata);
	   
   }
   function getdevicejsondata(msg2)
   {
	  var  json= eval(msg2);
	  rs232data=json.dp.parameters.Rs232;
	  analogdata=json.dp.parameters.Analog;
	  digitaldata=json.dp.parameters.Digital;
	  
	  $("#analoglisttt").html(getanalogdata2());
	  $("#digitallisttt").html(getDigitaldata2());
	  $("#rs232listtt").html(getRS232data2());
   }
   function getanalogdata2()
   {
	   var conten="";
		var select = '<select name="analogdata" id="analogdata"  class="chosen-select"  style="width: 100%;"><option value="">Please Select</option>';
		for (var i = 0; i < analogdata.length; i++) {
			conten = conten+ ' <option  value="'+analogdata[i].analogname+'">'+ analogdata[i].analogname + '</option>';
		}
		var html=select + conten + "</select>";
		//alert(html);
		return  html;
   }
   
   function getDigitaldata2()
   {
	   var conten="";
		var select = '<select name="digitaldata" id="digitaldata"  class="chosen-select"  style="width: 100%;"><option value="">Please Select</option>';
		for (var i = 0; i < digitaldata.length; i++) {
			conten = conten+ ' <option  value="'+digitaldata[i].parametername+'">'+ digitaldata[i].parametername + '</option>';
		}
		var html=select + conten + "</select>";
		//alert(html);
		return  html;
   }
   
   function getRS232data2()
   {
	   var conten="";
		var select = '<select name="rs232data" id="rs232data"  class="chosen-select"  style="width: 100%;"><option value="">Please Select</option>';
		for (var i = 0; i < rs232data.length; i++) {
			conten = conten+ ' <option  value="'+rs232data[i].parametername+'">'+ rs232data[i].parametername + '</option>';
		}
		var html=select + conten + "</select>";
		//alert(html);
		return  html;
   }
   
   $(document).ready(function () {
   	getdata();
 });
   function addanalogRow() 
   {
   	var table = document.getElementById("myanalogTableData");
       var rowCount = table.rows.length;
       var row = table.insertRow(rowCount);
       row.insertCell(0).innerHTML= '<tr><td>'+getanalogdata2()+'</td>';
       row.insertCell(1).innerHTML= '<td><input type="text" name="unit[]" placeholder="Enter Unit" id="unit[]" ></td>'
   	   row.insertCell(2).innerHTML= '<td><input type="text" name="formula[]" placeholder="Enter Formula" id="formula[]"></td>';
       row.insertCell(3).innerHTML= '<td><a class="btn btn-danger"   onClick="Javacsript:deleteanalogRow(this)"><i class="glyphicon glyphicon-trash icon-white"></i></a></td></tr>';
       mainflag++;
   }
  function deleteanalogRow(obj) 
   {
     //  alert(total1[index]);
       var index = obj.parentNode.parentNode.rowIndex;
       //alert(index);
       var table = document.getElementById("myanalogTableData");
       table.deleteRow(index);
   	mainflag--;
   }
   
    function adddigitalRow() 
   {
   	var table = document.getElementById("mydigitalTableData");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    row.insertCell(0).innerHTML=  '<tr><td>'+getDigitaldata2()+'</td>';
    row.insertCell(1).innerHTML= '<td><select name="dreverse[]" id="dreverse[]"><option value="True">True</option><option value="False">False</option></select></td>'
	row.insertCell(2).innerHTML= '<td><a class="btn btn-danger"   onClick="Javacsript:deletedigitalRow(this)"><i class="glyphicon glyphicon-trash icon-white"></i></a></td></tr>';
    mainflag++;
   }
   
   function deletedigitalRow(obj) 
   {
     //  alert(total1[index]);
       var index = obj.parentNode.parentNode.rowIndex;
       //alert(index);
       var table = document.getElementById("mydigitalTableData");
       table.deleteRow(index);
   		mainflag--;
     
   }
   
   function addrs232Row() 
   {
   	var table = document.getElementById("myrs232TableData");
  
       var rowCount = table.rows.length;
       var row = table.insertRow(rowCount);
       row.insertCell(0).innerHTML=  '<tr><td>'+getRS232data2()+'</td>';
       row.insertCell(1).innerHTML= '<td><select name="rsreverse[]" id="rsreverse[]"><option value="True">True</option><option value="False">False</option></select></td>'
   	row.insertCell(2).innerHTML= '<td><a class="btn btn-danger"   onClick="Javacsript:deleters232Row(this)"><i class="glyphicon glyphicon-trash icon-white"></i></a></td></tr>';
       mainflag++;
   }
   
    function deleters232Row(obj) 
    
   {
     //  alert(total1[index]);
       var index = obj.parentNode.parentNode.rowIndex;
       //alert(index);
       var table = document.getElementById("myrs232TableData");
       table.deleteRow(index);
   	mainflag--;
   }
    
    function tabledata(){
    	alert("in tabledata");
    	var oTable = document.getElementById('myanalogTableData');
    	 var rowLength = oTable.rows.length;
    	 
        alert("rowLength::"+rowLength);
         for (i = 1; i < rowLength; i++){
             var oCells = oTable.rows.item(i).cells;
			 var cellLength = oCells.length;
			 alert("cellLength::"+cellLength);
			 
			 for(var j=0; j < cellLength; j++){
			 var cellVal = oCells.item(j).textContent;
                    alert("cellVal::"+cellVal);
                 }
          }      
    }
  </script> 
      <div class="row">
        <div class="col-xs-12">
	<div class="box">
            <div class="box-header">
              <h3 class="box-title">Device Profile</h3>
            </div> 
   <form action="admin/savedeviceprofile" method="post">
    <div class="box-body">
       <div class="row">
          <div class="col-md-12" >
                 <label class="control-label" >Dashboard: </label>
                <div id="dashboardlist"></div>
          </div>
       </div>
       
         <div class="row">
          <div class="col-md-12" >
                 <label class="control-label" >Device: </label>
                <div id="devicelist"></div>
          </div>
       </div>
       
       <div class="row">
          <div class="col-md-3" >
                 <label class="control-label" >Digital : </label><br/>
                <div id="digitallisttt"></div>
          </div>
       
          <div class="col-md-3" >
                 <label class="control-label" >Analogs: </label><br/>
                <div id="analoglisttt"></div>
          </div>
          
          <div class="col-md-3" >
                 <label class="control-label" >RS 232: </label><br/>
                <div id="rs232listtt"></div>
          </div>
          <div class="col-md-3">
           <label class="control-label" >Add: </label><br/>
          <input type="button" class="btn btn-success" id="add" value="Add" onClick="Javascript:addanalogRow(this)" style="margin-left: 20px;">
          </div>
          </div>
       <br/>
       
       <h2> Analog Data <input type="button" class="btn btn-success" id="add" value="Add" onClick="Javascript:addanalogRow(this)" style="margin-left: 20px;"></h2> 
      
      <table  class="table table-striped table-bordered displays" name="myanalogTableData" id="myanalogTableData" style="width: 100%;float:left;" >  
	              <thead>
                  <tr>
   					<th><label>Analog Input</label></th>
   					<th><label>Unit</label></th>
                    <th><label>Formula</label></th>
                   </tr>
                  </thead> 
      </table>
      
       <h2> Digital Data <input type="button" class="btn btn-success" id="add" value="Add" onClick="Javascript:adddigitalRow(this)" style="margin-left: 20px;"></h2>
      <table  class="table table-striped table-bordered displays"  id="mydigitalTableData" style="width: 100%;float:left;" >  
	              <thead>
                  <tr>
   					<th><label>Digital Input</label></th>
   					<th><label>Reverse</label></th>
                   </tr>
                  </thead> 
      </table>
      
       <h2> RS232 Data <input type="button" class="btn btn-success" id="add" value="Add" onClick="Javascript:addrs232Row(this)" style="margin-left: 20px;"></h2>
      <table  class="table table-striped table-bordered displays"  id="myrs232TableData" style="width: 100%;float:left;" >  
	              <thead>
                  <tr>
   					<th><label>RS232</label></th>
   					<th><label>Reverse</label></th>
                   </tr>
                  </thead> 
      </table>
   </div>
   <input type="submit" class="btn btn-primary" id="add" style="margin-left: 20px;">
   </form>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
   
<script>

function deletealert(id)
{
        swal({
            title: "Are You Sure to Deleted Selected Componet Data??",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, Deleted it!",
            closeOnConfirm: false
        }, function () {
        	callDELETEJSONAjax("componet/"+id,deletejsondata);
        });
}
function deletejsondata(json)
{
	 swal("successfully Deleted.", "success");
   	 getdata();
}

  function inittable() 
  {
  $('#expiryreport')
  .dataTable(
  {
	              
	            
  "sPaginationType" : "full_numbers",
  "oLanguage" : {
  "sLengthMenu" : "Entries per page:<span class='lenghtMenu'> _MENU_</span><span class='lengthLabel'></span>"
  },
  //"sDom" : '<"tbl-tools-searchbox"fl<"clear">>,<"tbl_tools"CT<"clear">>,<"table_content"t>,<"widget-bottom"p<"clear">>',
   "sDom": 'T<"clear">lfrtip',
  "oTableTools" : {
  "sSwfPath" : "plugins/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
  }
             
  
               
  });
  $("div.tbl-tools-searchbox select").addClass(
  'tbl_length');
  }
</script>
</body>
</html>
