function getEmployeurBySearch(path) {
    var inputSearch = document.getElementById("inputName").value.trim();

    if (!inputSearch) {
        console.error("Champ de recherche vide !");
        return;
    }

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
            tbody.innerHTML = ""

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
                linkVoir.href = `${path}/donneesEmployeur/getAgents?id_emp=${employeur.id_emp}`;
                linkVoir.className = "text-primary";
                linkVoir.textContent = "Voir";
                cellVoir.appendChild(linkVoir);
                row.appendChild(cellVoir);

                tbody.appendChild(row);
            });
        })
        .catch(error => console.error("Erreur lors de la requête :", error));
}
