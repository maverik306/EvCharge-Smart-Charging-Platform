
$htmlFile = "c:\\Users\\User\\eclipse-workspace\\EvChargingApp\\EV_Charging_App_Complete_Documentation.html"
$docxFile = "c:\\Users\\User\\eclipse-workspace\\EvChargingApp\\EV_Charging_App_Complete_Project_Documentation.docx"

try {
    $word = New-Object -ComObject Word.Application
    $word.Visible = $false
    $doc = $word.Documents.Open($htmlFile)
    
    # Save as native Word docx (16 = wdFormatXMLDocument)
    $doc.SaveAs2($docxFile, 16)
    $doc.Close()
    $word.Quit()
    [System.Runtime.Interopservices.Marshal]::ReleaseComObject($word) | Out-Null
    Write-Host "DOCX creation successful: " (Test-Path $docxFile)
} catch {
    Write-Host "Word COM Error: " $_.Exception.Message
}
