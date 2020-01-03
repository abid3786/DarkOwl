function uploadFile(inFile, urlPart) {
    let formData = new FormData(inFile);
    try {
       let r = fetch(urlPart, {method: "POST", body: formData});
       console.log('HTTP response code:',r.status);
    } catch(e) {
       console.log('we have problem...:', e);
    }
    return false;
}

async function postForm(urlPart) {
    var fileForm = document.getElementById("logon_form");
    var fileIn = document.getElementById("urlFile");
    var sBar = document.getElementById("statusBar");
    var footer1 = document.getElementById("footer");
    footer1.innerHTML = '<span style="color:red"></span>';
    footer1.style.textAlign = "left";
    footer1.style.left = "151px";
    if(fileIn.files.length > 0) {
        uploadFile(fileForm, urlPart);
        var id = setInterval(progBarUpdate, 100);
        function progBarUpdate() {
            getStatus().then(function(v) {
                if(v.status >= 100) {
                    footer1.innerHTML = 'Message: <span style="color:#35BDF5;">Processed ' + v.UrlCount + ' in ' + v.timeInMillis + ' milliseconds </span>';
                    clearInterval(id);
                }
                sBar.style.width = v.status + '%';
            });
        }
    } else {
        footer1.innerHTML = 'Message: <span style="color:red">Please select a file, that contains URLs</span>';
    }
    return false;
}

async function getStatus() {
    const statusRes = await fetch('/status');
    const p = await statusRes.json();
    return p;
}
