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

window.addEventListener('resize', adjustTableAlignment);
