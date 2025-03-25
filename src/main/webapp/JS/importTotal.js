function importTotal(path) {
    console.log("Passage dans importTotal");

    const fileInput = document.getElementById("exampleInputFile");
    const file = fileInput.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const content = e.target.result;

            const lines = content.split("\n");

            const data = lines
                .filter(line => line.trim() !== '')
                .map(line => {
                    const columns = line.split(";");
                    columns[0] = columns[0].substring(0, 13);  // Trim noInsee to 13 characters
                    columns[1] = columns[1].replace(",", ".");  // Convert amount to correct format
                    return columns;
                });

            // Préparation du tableau d'objets avec les champs requis
            const jsonData = data.map(item => ({
                noInsee: item[0],
                montant: item[1],
                employeur: item[2].trim()
            }));

            console.log(jsonData);

            fetch(path + '/importTotal/insert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jsonData)
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
        };
        reader.readAsText(file);
    }
}
