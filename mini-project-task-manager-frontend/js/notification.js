const mainArea = document.querySelector(".main-area");
      const header = document.querySelector("header");

      mainArea.addEventListener("scroll", () => {
        if (mainArea.scrollTop > 0) {
          header.classList.add("blurred");
        } else {
          header.classList.remove("blurred");
        }
      });


document.querySelectorAll(".notification-card").forEach(card => {
    card.addEventListener("click", () => {
      location.href = "./notification_detail.html";
    });
  });



document.querySelectorAll("textarea").forEach((textarea) => {
  textarea.addEventListener("input", () => {
    textarea.style.height = "auto";
    textarea.style.height = textarea.scrollHeight + "px";

    const parent = textarea.closest("#fix");
    if (parent) {
      parent.style.height = "auto";
    }

    const card = textarea.closest(".notification-title-card, .notification-summation-card, .notification-content-card");
    if (card) {
      card.style.height = "auto";
    }
  });
});


document.querySelectorAll("textarea").forEach((textarea) => {
  textarea.addEventListener("input", () => {
    textarea.style.height = "auto";
    textarea.style.height = textarea.scrollHeight + "px";

    const parent = textarea.closest("#create");
    if (parent) {
      parent.style.height = "auto";
    }

    const card = textarea.closest(".notification-title-card, .notification-summation-card, .notification-content-card");
    if (card) {
      card.style.height = "auto";
    }
  });
});
