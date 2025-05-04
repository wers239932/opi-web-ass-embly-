let X;
let Y;
let R;

const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');
const width = canvas.width;
const height = canvas.height;
const max_radius = 180;
const max_r = 4;


function drawGraph(r){
    let radius = (max_radius/max_r)*r;
    if(isNaN(r))
        return;
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.fillStyle = '#3399ff';
    ctx.strokeStyle = 'black';

    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(width/2, height/2); 
    ctx.arc(width/2, height/2, radius/2, -3*Math.PI/2, -Math.PI, false);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(width/2, height/2 - radius);
    ctx.lineTo(width/2 + radius/2, height/2);
    ctx.lineTo(width/2, height/2);
    ctx.closePath();
    ctx.fill();

    ctx.fillRect(width/2, height/2, -radius, -radius / 2);

    ctx.beginPath();
    ctx.moveTo(0, height/2);
    ctx.lineTo(width, height/2);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(width/2, 0);
    ctx.lineTo(width/2, height);
    ctx.stroke();

    let startLine = height/2 - 5
    let endLine = height/2 + 5

    ctx.beginPath();
    ctx.moveTo(width/2 - radius, startLine);  
    ctx.lineTo(width/2 - radius, endLine);
    ctx.moveTo(width/2 - radius / 2, startLine);  
    ctx.lineTo(width/2 - radius / 2, endLine);
    ctx.moveTo(width/2 + radius / 2, startLine);  
    ctx.lineTo(width/2 + radius / 2, endLine);
    ctx.moveTo(width/2 + radius, startLine);  
    ctx.lineTo(width/2 + radius, endLine);
    ctx.stroke();

    startLine = width/2 - 5
    endLine = width/2 + 5
    ctx.beginPath();
    ctx.moveTo(startLine, height/2 - radius);  
    ctx.lineTo(endLine, height/2 - radius);
    ctx.moveTo(startLine, height/2 - radius/2); 
    ctx.lineTo(endLine, height/2 - radius/2);
    ctx.moveTo(startLine, height/2 + radius/2);  
    ctx.lineTo(endLine, height/2 + radius/2);
    ctx.moveTo(startLine, height/2 + radius);  
    ctx.lineTo(endLine, height/2 + radius);
    ctx.stroke();



    ctx.font = 'bold 16px Arial';
    ctx.fillStyle = 'black';
    ctx.fillText('x', width - 15, height/2 - 15); 
    ctx.fillText('y', width / 2 + 15, 15); 

     
    ctx.fillText('-R', width/2 - radius, height / 2 - 15); 
    ctx.fillText('-R/2', width/2 - radius/2, height / 2 - 15); 
    ctx.fillText('R/2', width/2 + radius/2, height / 2 - 15); 
    ctx.fillText('R', width/2 + radius, height / 2 - 15); 

    ctx.fillText('-R', width/2 + 15, height / 2 - radius); 
    ctx.fillText('-R/2', width/2 + 15, height / 2 - radius/2); 
    ctx.fillText('R/2', width/2 + 15, height / 2 + radius/2); 
    ctx.fillText('R', width/2 + 15, height / 2 + radius); 

    const arrowSize = 10; 
    ctx.beginPath();
    ctx.moveTo(width-1, height/2); 
    ctx.lineTo(width - arrowSize, height/2 - arrowSize/2);
    ctx.stroke();
    ctx.moveTo(width - arrowSize, height/2 + arrowSize/2);
    ctx.lineTo(width-1, height/2); 
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(width/2, 1);
    ctx.lineTo(width/2 - arrowSize/2, arrowSize); 
    ctx.stroke();
    ctx.moveTo(width/2 + arrowSize/2, arrowSize); 
    ctx.lineTo(width/2, 1); 
    ctx.stroke();
}
function drawPoint(x, y, r, result){
    let radius = (max_radius/max_r)*r;
    if(isNaN(r) || isNaN(x) || isNaN(y))
        return;
    let xProjection = radius / r * x;

    let yProjection = radius / r * y
    ctx.fillStyle = result ? 'green': 'red';
    ctx.beginPath();
    ctx.moveTo(width/2 + xProjection,  height/2 - yProjection);
    ctx.arc(width/2 + xProjection, height/2 - yProjection, 5, 2*Math.PI , 0, false);
    ctx.closePath();
    ctx.fill();
}

function handleClick(event) {

    const rect = canvas.getBoundingClientRect();
    console.log(event.clientX, event.clientY, rect.height)
    const x = event.clientX - rect.left - rect.width/2;
    const y = -(event.clientY - rect.top - rect.height/2);
    let radius = (max_radius/max_r)*R;
    X = Math.round(x/(radius)*R);
    X = X > 4 ? 4: X;
    X = X < -4 ? -4: X;
    Y = y/(radius)*R;
    Y = Y > 3 ? 3: Y;
    Y = Y < -5 ? -5: Y;

    const button = document.getElementById("coordinateForm:canvasHiddenButton");
    const xInput = document.getElementById("coordinateForm:canvasX");
    const yInput = document.getElementById("coordinateForm:canvasY");

    xInput.value = X;
    yInput.value = Y;

    button.click();
}
function getR() {
    const inputField = document.getElementById('main-form:r-value');
    R = inputField.value;
    drawGraph(R)
}
function drawAllPoints(){
    getR();
    const str = document.getElementById('main-form:hiddenResults').value;
    const jsonString = str.replace(/([{,])\s*(\w+):/g, '$1"$2":');
    const points = JSON.parse(jsonString);

    points.forEach((point) => drawPoint(point.x, point.y, point.r, point.result));
}
function drawPointsEvent(data){
    const status = data.status;
    if(status === "success"){
        drawAllPoints()
    }
}

canvas.addEventListener('click', handleClick);
drawAllPoints();

