let timeout;

document.getElementById("inputName").addEventListener("input", function() {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        rechercherEmployeur(this.value);
    }, 300);
});

function rechercherEmployeur(valeur) {
    console.log("Recherche de l'employeur :", valeur);
    getEmployeurBySearch(path);
}

function getEmployeurBySearch(path) {
    var inputSearch = document.getElementById("inputName").value.trim().toUpperCase();

    fetch(`${path}/rechercheEmployeur/search?recherche=${encodeURIComponent(inputSearch)}`, {
        method: 'GET',
        headers: { "Accept": "application/json" }
    })
        .then(response => response.json())
        .then(data => {
            console.log("Réponse du serveur :", data);

            if (!Array.isArray(data)) {
                console.error("Erreur : la réponse n'est pas un tableau", data);
                return;
            }

            var tbody = document.getElementById("employeursTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";

            data.forEach(employeur => {
                var row = document.createElement("tr");

                var cellNom = document.createElement("td");
                cellNom.textContent = employeur.lib_emp;
                row.appendChild(cellNom);

                var cellMail = document.createElement("td");
                cellMail.textContent = employeur.mail_emp || 'N/A';
                row.appendChild(cellMail);

                var cellVoir = document.createElement("td");

                var linkVoir = document.createElement("a");
                linkVoir.className = "text-primary";  // Applique une classe de style
                linkVoir.textContent = "Voir";  // Texte du lien
                var url = `${path}/donneesEmployeur?id_emp=${encodeURIComponent(employeur.id_emp)}`;
                linkVoir.href = url;


                cellVoir.appendChild(linkVoir);
                row.appendChild(cellVoir);
                tbody.appendChild(row);
            });
        })
        .catch(error => console.error("Erreur lors de la requête :", error));
}


