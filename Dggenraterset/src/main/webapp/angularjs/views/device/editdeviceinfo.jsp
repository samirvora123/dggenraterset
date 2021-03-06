<script src="js/jquery.validate.min.js"></script>
      <style type="text/css">
    label.valid {
        width: 24px;
        height: 24px;
        background: url(./img/valid.png) center center no-repeat;
        display: inline-block;
        text-indent: -9999px;
    }
    label.error {
        font-weight: bold;
        color: red;
        padding: 2px 8px;
        margin-top: 2px;
    }
    em.error {
        background:url("img/validationimg/unchecked.gif") no-repeat 0px 0px;
        padding-left: 15px;
        margin-top:5px;
        font-style:italic;
    }
    em.success {
        background:url("img/validationimg/checked.gif") no-repeat 0px 0px;
        padding-left: 15px;
    }

    form.cmxform label.error {
        margin-left: auto;
        width: 250px;
    }
    em.error { color: black; }
    #warning { display: none; }
    </style>
     <script type="text/javascript">
  	function clear_form_elements(ele)
  	{
  	    $(ele).find(':input').each(function() {
  	        switch(this.type) {
  	            case 'password':

  	           case 'select-multiple':

  	            case 'select-one':

  	            case 'text':
  	            	
  	          case 'number':

  	            case 'textarea':

  	               $(this).val('');

  	                break;

  	           case 'checkbox':

  	            case 'radio':

  	                this.checked = false;

  	        }

  	    });

  	 

  	}
  	
     function addbecondata() 
     {
    	  var postForm = $( '.form-horizontal' );
    	 var jsonData = function( form ) {
    		    var arrData = form.serializeArray(),
    		        objData = {};
    		     
    		    $.each( arrData, function( index, elem ) {
    		        objData[elem.name] = elem.value;
    		    });
    		     
    		    return JSON.stringify( objData );
    		};
    		 console.log(jsonData( postForm ));
    		 callPUTJSONAjax("admin/updatedeviceinfo/"+$("#cid").val(),jsonData( postForm ),savejsondata); 
     }
     
 function savejsondata(JSONObject) 
 {
	 JSONObject=JSON.parse(JSONObject);
	// alert(JSONObject+"         "+JSONObject['status']);
                 if (JSONObject['status'])
                 {
                         swal({
                             title: JSONObject['message'],
                             text: "Successfully Updated !",
                             type: "success"
                         });
                     //alert("Device Added successfully");
                 }else
                	 {
                	 swal({
                         title: "Get Error",
                         text: "Get Error",
                         type: "error"
                     });
                	}

                 getdata();  
            }
     
     $(document).ready(function() {
    	 
    	 callGETJSONAjax("admin/profilename",getprofilename);
  	     function getprofilename(msg2){
  			var conten = "";
  			var msg = eval(msg2);

  			var select = '<select name="prid" id="prid"  class="chosen-select" data-placeholder="Select a Profilename" style="width: 100%;" >';
  			for (var i = 0; i < msg.length; i++) {
  				//alert(msg[i].profilename)
  				conten = conten
  						+ ' <option  value="'+msg[i].prid+'">'
  						+ msg[i].profilename + '</option>';
  			}
  			$("#profilenames").html(select + conten + "</select>");
  		   
  		   
  	   }
    	 
    	 $('.form-horizontal').validate({
             rules: {
            	 prmname: {
                     required: true
                 },
                 prmtype:
                         {
                             required: true
                         }
             },
             highlight: function(label) {
                 $(label).closest('.control-group').addClass('error');
             },
             success: function(label) {
                 label.text('OK!').addClass('valid').closest('.control-group').addClass('success');
             },
             messages:
                     {
                         required: "Please enter required value"
                     }, submitHandler: function(form) {
                    	 addbecondata();
             }
         });
    	 
    	callGETJSONAjax("admin/deviceinfolist/"+$("#cid").val(),getobjectjsondata); 
    	 
     });
     function getobjectjsondata(JSONObject)
     {
    	  var  json= eval(JSONObject);
    	  
    	$("#devicename").val(json['devicename']);
        $("#profilenames").val(json['profilename']);
    
     }
     </script>
    <form class="form-horizontal" style="margin: 10px;">
      <div id="displaystatus"></div>  
                 <div class="row"><div class="col-lg-4">
                    <label class="control-label" >Device Name: </label>
                      <input type="text" class="form-control"  placeholder="Enter Device Name" name="devicename"  id="devicename" />
                   </div>
                </div> 
  				 <div class="row"><div class="col-lg-4">
                   
                <label class="control-label" >Device profile:</label>
                 <div id="profilenames" ></div>
                </div> </div>
                    
					<div align="center" style="margin-top: 2%;">
                        <input class="btn btn-primary" type="submit" value="SAVE" >
                        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                    </div>
                    	</form>