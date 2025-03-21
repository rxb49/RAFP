
function updateEmployerDetails() {
    var select = document.getElementById("idEmployeurs");
    var selectedOption = select.options[select.selectedIndex];
    var name = selectedOption.getAttribute("data-name");
    var email = selectedOption.getAttribute("data-email");

    document.getElementById("nomEmployeurUpdate").value = name;
    document.getElementById("mailEmployeurUpdate").value = email;
}

function insertEmployeur(path){
    let nomEmployeur = document.getElementById("nomEmployeur").value;
    let mailEmployeur = document.getElementById("mailEmployeur").value;
    fetch(path+'/gestionEmployeur/add', {
        method: 'POST',
        body: JSON.stringify({
            mail_emp: mailEmployeur,
            lib_emp: nomEmployeur,
        }),
        headers: {
            "Content-type":
            "application/json"
        }
    })
        .then(response => response.text())
        .then(text => {
            if(text === ""){
                Swal.fire({
                    icon: "success",
                    title: "L'employeur est ajouté",
                });
                appelModifier(path);
            }else{
                Swal.fire({
                    icon: "error",
                    title: text,
                });
            }
            document.getElementById('nomEmployeur').value = '';
            document.getElementById('mailEmployeur').value = '';
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}

function appelModifier(path){
    fetch(path+'/gestionEmployeur/modifier', {

    })
        .then(response => response.text())
        .then(text => {
            document.getElementById("cadre1").innerHTML = text;

        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}


function updateEmployeur(path){
    let idEmployeur = document.getElementById("idEmployeurs").value;
    let nomEmployeur = document.getElementById("nomEmployeurUpdate").value;
    let mailEmployeur = document.getElementById("mailEmployeurUpdate").value;
    fetch(path+'/gestionEmployeur/update', {
        method: 'POST',
        body: JSON.stringify({
            id_emp: idEmployeur,
            lib_emp: nomEmployeur,
            mail_emp: mailEmployeur
        }),
        headers: {
            "Content-type":
                "application/json"
        }
    })
        .then(response => response.text())
        .then(text => {
            if(text === ""){
                Swal.fire({
                    icon: "success",
                    title: "L'employeur est modifié",
                });
                appelModifier(path);
            }else{
                Swal.fire({
                    icon: "error",
                    title: text,
                });
            }
            appelModifier(path);
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}





