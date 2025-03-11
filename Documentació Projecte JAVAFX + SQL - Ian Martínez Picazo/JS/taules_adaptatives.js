/*
    En carregar el script, troba totes les taules a la página.
    i crea un contenidor amb una classe per a cadascun.
*/
window.onload = function() {
    const tables = document.querySelectorAll('table');
    tables.forEach(table => {
        const div = document.createElement('div');
        div.classList.add('deslitzar_horitzontal');
        table.parentNode.insertBefore(div, table);
        div.appendChild(table);
    });
    adjustTableAlignment();
};

// Si una taula es més gran que la finestra, canvia alguns dels seus estils.
function adjustTableAlignment() {
    const tableContainers = document.querySelectorAll('.deslitzar_horitzontal');
    tableContainers.forEach(tableContainer => {
        const table = tableContainer.querySelector('table');
        if (table.scrollWidth > tableContainer.clientWidth) {
            tableContainer.style.justifyContent = 'flex-start';
            tableContainer.style.overflowX = 'auto';
        } else {
            tableContainer.style.justifyContent = 'center';
            tableContainer.style.overflowX = 'hidden';
        }
    });
}

// Executa una funció cada vegada que la finestra canvia de tamany.
window.addEventListener('resize', adjustTableAlignment);
