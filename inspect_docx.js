/**
 * Script to inspect the DOCX XML structure and find placeholder paragraphs
 */
const JSZip = require('C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\jszip');
const fs = require('fs');

async function inspectDocx() {
  const docPath = 'C:\\Users\\User\\Downloads\\PROJECT CONTINUE.docx';
  const data = fs.readFileSync(docPath);
  const zip = await JSZip.loadAsync(data);
  
  // List all files in the docx
  console.log('=== FILES IN DOCX ===');
  Object.keys(zip.files).forEach(name => {
    console.log(name);
  });
  
  // Get the document.xml
  const docXml = await zip.file('word/document.xml').async('string');
  
  // Find placeholder text
  const placeholderSearch = '[Insert project screenshot / diagram here]';
  const idx = docXml.indexOf(placeholderSearch);
  console.log('\n=== PLACEHOLDER FOUND AT INDEX:', idx, '===');
  
  // Show context around first placeholder
  if (idx > 0) {
    const context = docXml.substring(Math.max(0, idx - 200), idx + 300);
    console.log('\n=== CONTEXT AROUND FIRST PLACEHOLDER ===');
    console.log(context);
  }
  
  // Count placeholders
  let count = 0;
  let pos = 0;
  while ((pos = docXml.indexOf(placeholderSearch, pos)) !== -1) {
    count++;
    pos += placeholderSearch.length;
  }
  console.log('\n=== TOTAL PLACEHOLDERS:', count, '===');
}

inspectDocx().catch(console.error);
