// Script per navegar dinamicament a altres págines.

function navegacioMenu() {
    /*
        Obté tots els botons de navegació i els assigna un escoltador d'esdeveniments.
        Al fer clic en un botó, redirecciona a l'usuari a la pàgina corresponent.
    */
    const pagines = document.querySelectorAll("main div");
    pagines.forEach((pagina, index) => {
        pagina.addEventListener("click", () => {
            if (index != 2){
                window.location.href = `./${pagina.id}.html`;
            }else{
                window.open("./Manual d'ús de l'aplicació.pdf", '_blank');
            }
        });
    });
};

function navegacioAltres() {
    const boto_menu = document.getElementById("boto_menu");
    boto_menu.addEventListener("click", () => {
        window.location.href = "./index.html";
    });
};