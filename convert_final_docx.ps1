
$htmlFile = "c:\\Users\\User\\eclipse-workspace\\EvChargingApp\\EV_Charging_App_Report_25_30_Pages.html"
$docxFile = "c:\\Users\\User\\eclipse-workspace\\EvChargingApp\\EV_Charging_App_Project_Report_Final.docx"

try {
    $word = New-Object -ComObject Word.Application
    $word.Visible = $false
    $doc = $word.Documents.Open($htmlFile)
    
    # Save as native Word docx (16 = wdFormatXMLDocument)
    $doc.SaveAs2($docxFile, 16)
    
    $pages = $doc.ComputeStatistics([Microsoft.Office.Interop.Word.WdStatistic]::wdStatisticPages)
    $words = $doc.ComputeStatistics([Microsoft.Office.Interop.Word.WdStatistic]::wdStatisticWords)
    
    $doc.Close()
    $word.Quit()
    [System.Runtime.Interopservices.Marshal]::ReleaseComObject($word) | Out-Null
    
    Write-Host "DOCX creation successful: " (Test-Path $docxFile)
    Write-Host "Exact Page Count in Word: " $pages
    Write-Host "Total Words in Word: " $words
} catch {
    Write-Host "Word COM Error: " $_.Exception.Message
}
