function getAgentByEmployeur(path) {
    var selectElement = document.getElementById("idEmployeurs");
    var idEmployeur = selectElement.value;

    fetch(path + '/donneesEmployeur/getAgents', {
        method: 'POST',
        body: JSON.stringify({
            idEmployeur: idEmployeur,
        }),
        headers: {
            "Content-type": "application/json"
        }
    })
        .then(response => response.json())
        .then(data => {
            var tbody = document.getElementById("agentsTable").getElementsByTagName("tbody")[0];
            tbody.innerHTML = "";

            data.forEach(agent => {
                var row = document.createElement("tr");

                var cellNom = document.createElement("td");
                cellNom.textContent = agent.insee;
                row.appendChild(cellNom);

                var cellMontant = document.createElement("td");
                cellMontant.textContent = agent.mnt_retour;
                row.appendChild(cellMontant);

                var cellModifier = document.createElement("td");
                var linkModifier = document.createElement("a");
                linkModifier.href = "${pageContext.request.contextPath}/modifierAgent";
                linkModifier.className = "text-primary";
                linkModifier.textContent = "Modifier";
                cellModifier.appendChild(linkModifier);
                row.appendChild(cellModifier);

                var cellSupprimer = document.createElement("td");
                var linkSupprimer = document.createElement("a");
                linkSupprimer.href = "#";
                linkSupprimer.className = "text-danger";
                linkSupprimer.textContent = "Supprimer";
                linkSupprimer.onclick = function() { confirmDelete(agent.insee); };
                cellSupprimer.appendChild(linkSupprimer);
                row.appendChild(cellSupprimer);

                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Erreur lors de la requête :", error);
        });
}
