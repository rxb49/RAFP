

function insertEmployeurRetour(path){
    var select = document.getElementById("idEmployeurs");
    var selectedOption = select.options[select.selectedIndex];
    var idEmployeur = selectedOption.value;
    let montant = document.getElementById("montant").value;
    const pathSegments = window.location.pathname.split('/');
    const noInsee = pathSegments[pathSegments.length - 1]; // Avant-dernier segment
    fetch(path+'/ajoutEmployeur/add', {
        method: 'POST',
        body: JSON.stringify({
            noInsee: noInsee,
            idEmployeur: idEmployeur,
            montant: montant
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
                    title: "Le retour est ajouté",
                });
            }else{
                Swal.fire({
                    icon: "error",
                    title: text,
                });
            }

        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}




