function draw() {

    const svg = document.getElementById("svg");
    svg.innerHTML = "";

    const scale = 0.8;

    const width = order.width * scale;
    const length = order.length * scale;

    const startX = 100;
    const startY = 100;

    // main frame
    const rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");

    rect.setAttribute("x", startX);
    rect.setAttribute("y", startY);
    rect.setAttribute("width", length);
    rect.setAttribute("height", width);
    rect.setAttribute("fill", "none");
    rect.setAttribute("stroke", "black");

    svg.appendChild(rect);

    if(order.toolShed) {

        const shed = document.createElementNS("http://www.w3.org/2000/svg", "rect");

        shed.setAttribute("id", "shed");

        shed.setAttribute("x", startX + length - (order.shedLength * scale));
        shed.setAttribute("y", startY);

        shed.setAttribute("width", order.shedLength * scale);
        shed.setAttribute("height", order.shedWidth * scale);

        shed.setAttribute("fill", "#ddd");
        shed.setAttribute("stroke", "black");

        svg.appendChild(shed);

        enableDrag(shed);
    }
}

function enableDrag(element) {

    let offsetX = 0;
    let offsetY = 0;
    let dragging = false;

    element.addEventListener("mousedown", (e) => {
        dragging = true;
    });

    document.addEventListener("mousemove", (e) => {

        if(!dragging) return;

        const svg = document.getElementById("svg");

        const pt = svg.createSVGPoint();
        pt.x = e.clientX;
        pt.y = e.clientY;

        const cursor = pt.matrixTransform(svg.getScreenCTM().inverse());

        element.setAttribute("x", cursor.x);
        element.setAttribute("y", cursor.y);
    });

    document.addEventListener("mouseup", () => {
        dragging = false;
    });
}