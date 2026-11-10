const mammoth = require('C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\mammoth');
const path = require('path');

const docPath = 'C:\\Users\\User\\Downloads\\PROJECT CONTINUE.docx';

mammoth.extractRawText({ path: docPath })
  .then(result => {
    const text = result.value;
    const lines = text.split('\n');
    console.log('=== DOCUMENT STRUCTURE (lines 290-600) ===');
    lines.slice(290, 600).forEach((line, i) => {
      if (line.trim()) {
        console.log(`[${290+i+1}] ${line}`);
      }
    });
    console.log('\n=== TOTAL LINES: ' + lines.length + ' ===');
  })
  .catch(err => console.error('Error:', err));
