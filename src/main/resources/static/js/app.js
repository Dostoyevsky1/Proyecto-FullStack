(() => {
  "use strict";

  const normalize = (text) =>
    text
      .normalize("NFD")
      .replace(/[\u0300-\u036f]/g, "")
      .toLowerCase()
      .trim();
  const showToast = (message) => {
    const element = document.getElementById("appToast");
    if (!element) return;
    document.getElementById("toastMessage").textContent = message;
    bootstrap.Toast.getOrCreateInstance(element, { delay: 4200 }).show();
  };

  document.querySelectorAll("[data-toast]").forEach((button) => {
    button.addEventListener("click", () => showToast(button.dataset.toast));
  });

  // Los formularios validan en el navegador; no envían ni almacenan información.
  document.querySelectorAll("form[data-demo-form]").forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      form.classList.add("was-validated");
      if (!form.checkValidity()) {
        form.querySelector(":invalid")?.focus();
        form.reportValidity();
        return;
      }
      if (form.dataset.demoForm === "navigate") {
        window.location.assign(form.action);
        return;
      }
      if (form.dataset.successModal) {
        bootstrap.Modal.getOrCreateInstance(
          document.getElementById(form.dataset.successModal),
        ).show();
      } else {
        const modal = form.closest(".modal");
        if (modal) bootstrap.Modal.getOrCreateInstance(modal).hide();
        showToast(
          form.dataset.successMessage ||
            "Datos preparados correctamente. Acción simulada.",
        );
      }
    });
  });

  const passwordButton = document.querySelector("[data-toggle-password]");
  passwordButton?.addEventListener("click", () => {
    const field = document.getElementById("password");
    const visible = field.type === "password";
    field.type = visible ? "text" : "password";
    passwordButton.setAttribute(
      "aria-label",
      visible ? "Ocultar contraseña" : "Mostrar contraseña",
    );
    passwordButton.setAttribute("aria-pressed", String(visible));
    passwordButton.querySelector("i").className = visible
      ? "bi bi-eye-slash"
      : "bi bi-eye";
  });

  // Los filtros actúan solo sobre los elementos ficticios escritos en el HTML.
  document.querySelectorAll("[data-filter-scope]").forEach((scope) => {
    let category = "todos";
    const search = scope.querySelector("[data-filter-search]");
    const items = Array.from(scope.querySelectorAll("[data-filter-item]"));
    const update = () => {
      const term = normalize(search?.value || "");
      let count = 0;
      items.forEach((item) => {
        const matchesCategory =
          category === "todos" ||
          item.dataset.category.split(" ").includes(category);
        const matchesText = normalize(
          item.dataset.search || item.textContent,
        ).includes(term);
        item.hidden = !matchesCategory || !matchesText;
        if (!item.hidden) count += 1;
      });
      scope.querySelectorAll("[data-result-count]").forEach((output) => {
        output.textContent = String(count);
      });
      const empty = scope.querySelector("[data-empty-state]");
      if (empty) empty.hidden = count > 0;
    };
    scope.querySelectorAll("[data-filter]").forEach((button) => {
      button.addEventListener("click", () => {
        category = button.dataset.filter;
        scope.querySelectorAll("[data-filter]").forEach((other) => {
          const active = other === button;
          other.classList.toggle("active", active);
          other.setAttribute("aria-pressed", String(active));
        });
        update();
      });
    });
    search?.addEventListener("input", update);
    scope
      .querySelector("[data-reset-filters]")
      ?.addEventListener("click", () => {
        if (search) search.value = "";
        scope.querySelector('[data-filter="todos"]')?.click();
      });
    update();
  });

  const globalSearch = document.getElementById("globalSearch");
  const globalResults = document.getElementById("globalResults");
  if (globalSearch && globalResults) {
    const closeSearch = () => {
      globalResults.hidden = true;
      globalSearch.setAttribute("aria-expanded", "false");
    };
    const updateSearch = () => {
      const term = normalize(globalSearch.value);
      let count = 0;
      globalResults.querySelectorAll("[data-search-item]").forEach((link) => {
        link.hidden = !normalize(link.dataset.searchItem).includes(term);
        if (!link.hidden) count += 1;
      });
      globalResults.querySelector(".search-empty").hidden = count > 0;
      globalResults.hidden = false;
      globalSearch.setAttribute("aria-expanded", "true");
    };
    globalSearch.addEventListener("focus", updateSearch);
    globalSearch.addEventListener("input", updateSearch);
    globalSearch.addEventListener("keydown", (event) => {
      if (event.key === "Escape") closeSearch();
      if (event.key === "ArrowDown" || event.key === "Enter") {
        const first = globalResults.querySelector("a:not([hidden])");
        if (first) {
          event.preventDefault();
          first.focus();
        }
      }
    });
    document.addEventListener("click", (event) => {
      if (!event.target.closest(".global-search")) closeSearch();
    });
    globalResults.addEventListener("keydown", (event) => {
      if (event.key === "Escape") {
        globalSearch.focus();
        closeSearch();
      }
    });
  }

  const productModal = document.getElementById("productModal");
  productModal?.addEventListener("show.bs.modal", (event) => {
    const card = event.relatedTarget?.closest(".product-card");
    if (!card) return;
    document.getElementById("productModalTitle").textContent =
      card.querySelector(".product-name").textContent;
    const image = document.getElementById("productModalImage");
    image.src = card.querySelector(".product-photo img").src;
    image.alt = card.querySelector(".product-photo img").alt;
    document.getElementById("productModalPrice").textContent =
      card.querySelector(".product-price").textContent;
    const details = card.querySelector(".product-details").cloneNode(true);
    details.hidden = false;
    document.getElementById("productModalDetails").replaceChildren(details);
  });

  const editModal = document.getElementById("editClientModal");
  editModal?.addEventListener("show.bs.modal", (event) => {
    const row = event.relatedTarget?.closest("tr");
    if (!row) return;
    document.getElementById("editClientName").value =
      row.querySelector(".client-name").textContent.trim();
    document.getElementById("editClientPhone").value = row
      .querySelector(".client-phone")
      .textContent.replace(/\s/g, "");
  });

  const sidebar = document.getElementById("sidebar");
  document.querySelectorAll("[data-sidebar-modal]").forEach((button) => {
    button.addEventListener("click", () => {
      const dialog = document.querySelector(button.dataset.sidebarModal);
      const menuIsOpen = sidebar.classList.contains("show");
      const returnFocus = menuIsOpen
        ? document.querySelector('[aria-controls="sidebar"]')
        : button;
      const openDialog = () => {
        dialog.addEventListener("hidden.bs.modal", () => returnFocus?.focus(), {
          once: true,
        });
        bootstrap.Modal.getOrCreateInstance(dialog).show(button);
      };

      // En móvil se muestra una sola ventana flotante a la vez.
      if (menuIsOpen) {
        sidebar.addEventListener("hidden.bs.offcanvas", openDialog, { once: true });
        bootstrap.Offcanvas.getOrCreateInstance(sidebar).hide();
      } else {
        openDialog();
      }
    });
  });
})();
