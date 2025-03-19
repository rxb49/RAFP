function getEmployeurBySearch(path) {
    var inputSearch = document.getElementById("inputName").value.trim().toUpperCase();

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


