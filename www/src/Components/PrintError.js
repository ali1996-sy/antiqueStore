const PrintError = (err, color = "red") => {
  if (!err) return;
  let message_board = document.querySelector("#message-board");
  if (!document.querySelector("#error")) {
    let errorDiv=document.createElement("div");
    errorDiv.id="error";
    errorDiv.innerHTML=`<div class="alert fade_error .fade"> <button aria-hidden="true" data-dismiss="alert" 
    class="close" type="button">×</button> <strong id="error-text">${err.message}</strong> </div>`;
    errorDiv.style.color = color;
    errorDiv.style.display="bloc";
   message_board.appendChild(errorDiv);
  }
  
  
    
  document.getElementsByClassName('close')[0].addEventListener('click',cache);

  setTimeout(cache,2000);
  function cache(){
    
    document.getElementById('message-board').innerHTML="";
    
      
    
  }
  
  
  
};

export default PrintError;
