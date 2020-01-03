function validateForm() {
    var f2 = document.forms["oneform"]["eMail"];
    if(! emptyCheck(f2, "E-Mail")) {
	return false;
    }
    if(! validateEmail(f2)) {
	return false;
    }
}

function emptyCheck(formField, fieldValue) {
    var y = formField.value;
    if (x == null || x == "") {
        alert(fieldValue + " must be filled out");
        return false;
    }
    return true;
}

function validateEmail(uemail)  {  
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
	if(uemail.value.match(mailformat))  {  
		return true;  
	} else {  
		alert("You have entered an invalid email address!");  
		uemail.focus();  
		return false;  
	}  
}  
