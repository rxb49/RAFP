function confirmDeleteEmployeur(path, employeur, id_emp, noInsee) {

    Swal.fire({
        title: "Êtes-vous sûr ?",
        text: "Voulez-vous vraiment supprimer " + employeur + " pour l'agent " + noInsee + " ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Oui, supprimer",
        cancelButtonText: "Annuler"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${path}/ajoutEmployeur/delete/${noInsee}/${id_emp}`, {
                method: 'DELETE',
                headers: {
                    "Content-type": "application/json"
                }
            })
                .then(response => response.text())
                .then(text => {
                    Swal.fire({
                        icon: "success",
                        title: text,
                    }).then(() => {
                        location.reload();
                    });

                })
                .catch(error => {
                    Swal.fire({
                        icon: "error",
                        title: "Erreur lors de la requête : " + error,
                    }).then(() => {
                        location.reload();
                    });
                });
        } else {
            console.log(employeur + " n'a pas été supprimé.");
        }
    });
}


function confirmDeleteAgent(agentInsee) {
    if (confirm("Voulez-vous vraiment supprimer l'agent avec l'INSEE " + agentInsee + " ?")) {
        console.log("Agent " + agentInsee + " supprimé.");
    }
}