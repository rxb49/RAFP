

function modifEmployeurRetour(path){
    let montant = document.getElementById("montant").value;
    const pathSegments = window.location.pathname.split('/');
    const noInsee = pathSegments[pathSegments.length - 2];
    const idEmployeur = pathSegments[pathSegments.length - 1];
    console.log(montant, noInsee, idEmployeur);
    fetch(path+'/ajoutEmployeur/modifier', {
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
                    title: "Le retour est modifier",
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




