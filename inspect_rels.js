/**
 * Inspect the word/_rels/document.xml.rels and existing images
 */
const JSZip = require('C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\jszip');
const fs = require('fs');

async function inspectDocx() {
  const docPath = 'C:\\Users\\User\\Downloads\\PROJECT CONTINUE.docx';
  const data = fs.readFileSync(docPath);
  const zip = await JSZip.loadAsync(data);
  
  const relsXml = await zip.file('word/_rels/document.xml.rels').async('string');
  console.log('=== RELATIONSHIPS ===');
  console.log(relsXml);
  
  const docXml = await zip.file('word/document.xml').async('string');
  
  // Check if there are existing drawings/images in the doc
  const drawingCount = (docXml.match(/w:drawing/g) || []).length;
  console.log('\n=== EXISTING DRAWINGS IN DOC:', drawingCount, '===');
  
  // Get the XML namespaces
  const nsMatch = docXml.match(/^<\?xml[^>]*\?>\s*<w:document[^>]*>/);
  if (nsMatch) {
    console.log('\n=== DOCUMENT ELEMENT START ===');
    console.log(nsMatch[0].substring(0, 1000));
  }
}

inspectDocx().catch(console.error);
