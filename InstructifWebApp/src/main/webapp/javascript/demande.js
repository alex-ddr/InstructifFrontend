$(document).ready(function () {

    const options = [];
    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "lister-matieres",

        },
        dataType: "json"
    })
            .done(function (matieres) {
                console.log(matieres); // affichage dans la console navigateur
                const dropdown = document.getElementById("matiere");
                
                matieres.forEach(function (matiere) {
                    const option = document.createElement("option");
                    option.value = matiere.id;
                    option.textContent = matiere.intitule;
                    dropdown.appendChild(option);
                });
            })
            .fail(function (xhr, status, error) {
                console.error("Erreur AJAX : " + status + " - " + error);
            });
            
            
    $('#envoyer-btn').on('click', function () {

        // Collect values
        const nom = $('#nom').val();
        const prenom = $('#prenom').val();
        const email = $('#email').val();
        const mdp = $('#mdp').val();
        const confirm = $('#confirm').val();
        const date_naissance = $('#date_naissance').val();
        const code = $('#code').val();
        const niveau = $('#niveau').val();

        // Champs obligatoires
        if (!nom || !prenom || !email || !mdp || !confirm || !date_naissance || !code || !niveau)
        {
            alert("Tous les champs doivent être remplis.");
            return;
        }

        // Password confirmation
        if (mdp !== confirm) {
            alert("Les mots de passe de correspondent pas.");
            return;
        }


        // AJAX request
        $.ajax({
            type: 'POST',
            url: './ActionServlet',
            data: {
                todo: "inscrire-eleve",
                nom: nom,
                prenom: prenom,
                email: email,
                mdp: mdp,
                date_naissance: date_naissance,
                code: code,
                niveau: niveau
            },
            dataType: "json"
        })
                .done(function (response) {
                    alert("Inscription réussie.");
                    window.location.href = "./index.html";
                })
                .fail(function (error) {
                    alert("L'inscription a échoué.");
                });
    });

});