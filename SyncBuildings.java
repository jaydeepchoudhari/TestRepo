<div id="newFaultDialogHeader" class="card-header">
  <div class="left-section">
    <span id="note">Click And Drag To Create New Fault</span>
    <span id="fileName">(20240321_175709_PCNemec_img_1.JPG)</span>
  </div>

  <div class="right-section">
    <button class="nav-button prev">
      <i class="fas fa-chevron-left"></i>
    </button>
    <button class="nav-button next">
      <i class="fas fa-chevron-right"></i>
    </button>
  </div>
</div>


  #newFaultDialogHeader {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 5%;
  background-color: #fff; /* optional */
}

.left-section {
  display: flex;
  flex-direction: column; /* stack spans vertically */
  justify-content: center;
}

#note {
  font-weight: 600;
}

#fileName {
  margin-top: 2px;
  font-size: 0.9em;
  color: #666;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 18px;
}

.nav-button:hover {
  color: #007bff;
}
