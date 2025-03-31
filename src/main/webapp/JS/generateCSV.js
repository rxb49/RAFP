function generateCSV(path) {
    console.log("Passage dans generateCSV");
    fetch(`${path}/calculRafp/generateCSV`, {
        method: 'GET',
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
        })
}

