$(document).ready(function () {
    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "lien-visio-eleve",
        },
        dataType: "json"
    })
            .done(function (lien_visio) {
                $('#rejoindre_visio_link').attr('href', lien_visio);
            })
            .fail(function (error) {
                alert("Erreur de lien visio.");
            });


    $('#seance-terminee').on('click', function () {
        let selectedValue = $('#evaluation').val();

        if (selectedValue === "") {
            let max = 0;
            $('#evaluation option').each(function () {
                const val = $(this).val();
                if (val !== "") {
                    const num = parseInt(val);
                    if (!isNaN(num) && num > max) {
                        max = num;
                    }
                }
            });
            selectedValue = max;
        }

        $.ajax({
            type: 'POST',
            url: './ActionServlet',
            data: {
                todo: "seance-terminee-eleve",
                note_eleve: selectedValue
            },
            dataType: "json"
        })
                .done(function (response) {
                    console.log("Fin de séance enregistrée");
                    window.location.href = "./demande.html";
                })
                .fail(function (error) {
                    alert("Erreur lors de l'enregistrement de la fin de séance.");
                });
    });
});
