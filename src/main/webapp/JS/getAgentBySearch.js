function getAgentBySearch(path) {
    var inputSearch = document.getElementById("inputName").value.trim().toUpperCase();

    if (!inputSearch) {
        console.error("Champ de recherche vide !");
        return;
    }

    fetch(`${path}/rechercheAgent/search?recherche=${encodeURIComponent(inputSearch)}`, {
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

            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";

            data.forEach(agent => {
                var row = document.createElement("tr");

                var cellPrenom = document.createElement("td");
                cellPrenom.textContent = agent.prenom;
                row.appendChild(cellPrenom);

                var cellNom = document.createElement("td");
                cellNom.textContent = agent.nom_usuel || 'N/A';
                row.appendChild(cellNom);

                var cellNo_insee = document.createElement("td");
                cellNo_insee.textContent = agent.no_insee || 'N/A';
                row.appendChild(cellNo_insee);

                var cellVoir = document.createElement("td");


                var buttonVoir = document.createElement("button");
                buttonVoir.type = "button";
                buttonVoir.className = "text-primary";
                buttonVoir.textContent = "Voir";

                buttonVoir.addEventListener("click", function () {
                    var form = document.createElement("form");
                    form.method = "POST";
                    form.action =  `${path}/donneesAgent`;

                    var inputId = document.createElement("input");
                    inputId.type = "hidden";
                    inputId.name = "no_insee";
                    inputId.value = agent.no_insee;

                    form.appendChild(inputId);
                    document.body.appendChild(form);
                    form.submit();
                });

                cellVoir.appendChild(buttonVoir);
                row.appendChild(cellVoir);

                tbody.appendChild(row);
            });
        })
        .catch(error => console.error("Erreur lors de la requête :", error));
}


