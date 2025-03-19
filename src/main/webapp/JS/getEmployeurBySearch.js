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


                var buttonVoir = document.createElement("button");
                buttonVoir.type = "button";
                buttonVoir.className = "text-primary";
                buttonVoir.textContent = "Voir";

                buttonVoir.addEventListener("click", function () {
                    var form = document.createElement("form");
                    form.method = "POST";
                    form.action =  `${path}/donneesEmployeur`;

                    var inputId = document.createElement("input");
                    inputId.type = "hidden";
                    inputId.name = "id_emp";
                    inputId.value = employeur.id_emp;

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


function getAgentByEmployeur(path, idEmployeur) {
    var totalMontantRetourDisplay = document.getElementById("totalMontantRetour");

    // Envoi de la requête POST avec l'ID employeur dans le corps de la requête
    fetch(path + '/donneesEmployeur', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify({ id_emp: idEmployeur })  // Envoi de l'ID employeur dans le corps de la requête
    })
        .then(response => response.json())  // Conversion de la réponse en JSON
        .then(data => {
            if (data.error) {
                console.error("Erreur du serveur :", data.error);
                return;
            }

            // Affichage des informations de l'employeur
            var employeurNomDisplay = document.getElementById("selectedEmployeur");
            employeurNomDisplay.textContent = "Nom de l'employeur : " + (data.employeur.lib_emp || 'Inconnu');

            // Remplir le tableau avec les données des agents
            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";  // Vide le corps du tableau avant de le remplir
            let totalMontant = 0;

            // Remplir le tableau avec les données des agents
            data.agents.forEach(agent => {
                var row = document.createElement("tr");

                // Créer les cellules pour chaque agent
                var cellInsee = document.createElement("td");
                cellInsee.textContent = agent.insee;
                row.appendChild(cellInsee);

                var cellNom = document.createElement("td");
                cellNom.textContent = agent.nom_usuel || 'N/A';
                row.appendChild(cellNom);

                var cellPrenom = document.createElement("td");
                cellPrenom.textContent = agent.prenom || 'N/A';
                row.appendChild(cellPrenom);

                var cellMontant = document.createElement("td");
                cellMontant.textContent = agent.mnt_retour + " €";
                row.appendChild(cellMontant);

                // Ajouter les liens pour modifier et supprimer
                var cellModifier = document.createElement("td");
                var linkModifier = document.createElement("a");
                linkModifier.href = path + "/modifierAgent?id_insee=" + agent.insee;
                linkModifier.className = "text-primary";
                linkModifier.textContent = "Modifier";
                cellModifier.appendChild(linkModifier);
                row.appendChild(cellModifier);

                var cellSupprimer = document.createElement("td");
                var linkSupprimer = document.createElement("a");
                linkSupprimer.href = "#";
                linkSupprimer.className = "text-danger";
                linkSupprimer.textContent = "Supprimer";
                linkSupprimer.onclick = function () {
                    confirmDelete(agent.insee);
                };
                cellSupprimer.appendChild(linkSupprimer);
                row.appendChild(cellSupprimer);

                tbody.appendChild(row);
                totalMontant += parseFloat(agent.mnt_retour) || 0;
            });

            // Affichage du montant total
            totalMontantRetourDisplay.textContent = "Montant retour total: " + totalMontant.toFixed(2) + "€";
        })
        .catch(error => console.error("Erreur lors de la requête :", error));
}

