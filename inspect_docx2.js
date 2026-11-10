/**
 * Full inspection of all placeholder paragraphs in document.xml
 * to understand XML structure around each one
 */
const JSZip = require('C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\jszip');
const fs = require('fs');

async function inspectDocx() {
  const docPath = 'C:\\Users\\User\\Downloads\\PROJECT CONTINUE.docx';
  const data = fs.readFileSync(docPath);
  const zip = await JSZip.loadAsync(data);
  
  const docXml = await zip.file('word/document.xml').async('string');
  
  // Find all placeholder paragraph blocks
  const placeholder = '[Insert project screenshot / diagram here]';
  const results = [];
  let pos = 0;
  let idx = 0;
  while ((pos = docXml.indexOf(placeholder, pos)) !== -1) {
    // Find the start of the containing paragraph
    const paraStart = docXml.lastIndexOf('<w:p ', pos);
    // Find the end of the containing paragraph
    const paraEnd = docXml.indexOf('</w:p>', pos) + '</w:p>'.length;
    
    // Get full paragraph XML
    const paraXml = docXml.substring(paraStart, paraEnd);
    
    // Get ~200 chars before (for context - what section heading precedes this)
    const before = docXml.substring(Math.max(0, paraStart - 500), paraStart);
    
    console.log(`\n=== PLACEHOLDER #${++idx} at pos ${pos} ===`);
    console.log('PARAGRAPH XML:', paraXml.substring(0, 300));
    console.log('BEFORE (last 300 chars):', before.slice(-300));
    
    pos += placeholder.length;
    results.push({ pos, paraStart, paraEnd, paraXml });
  }
  
  console.log('\n=== TOTAL:', idx, '===');
}

inspectDocx().catch(console.error);
