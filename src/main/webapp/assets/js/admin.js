function radioElements(element) {
    const elements = document.querySelectorAll(element);
    elements.forEach(function(e) {
        e.addEventListener('click', function() {
            const parent = this.closest('.group');
            if(!parent) return;
            const subs = parent.querySelectorAll(element);
            subs.forEach(function(item) {
                item.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
}