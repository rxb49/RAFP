

function modifEmployeurRetour(path, idEmployeur, noInsee){
    let montant = document.getElementById("montant").value;
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
                Swal.fire({
                    icon: "success",
                    title: text,
                });
        })
        .catch(error => {
            Swal.fire({
                icon: "error",
                title: 'Erreur lors de la requête :' + error,
            });
        });
}




