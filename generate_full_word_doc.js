const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

console.log("Generating Complete Project Documentation Word Document (.docx)...");

// Read files from codebase to include in the documentation
const readmeContent = fs.readFileSync(path.join(__dirname, 'README.md'), 'utf8');
const schemaContent = fs.readFileSync(path.join(__dirname, 'schema.sql'), 'utf8');
const pomContent = fs.readFileSync(path.join(__dirname, 'pom.xml'), 'utf8');
const dbPropsContent = fs.readFileSync(path.join(__dirname, 'db.properties'), 'utf8');

// Read models
const driverCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Driver.java'), 'utf8');
const vehicleCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Vehicle.java'), 'utf8');
const chargerCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Charger.java'), 'utf8');
const locationCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/ChargingLocation.java'), 'utf8');
const bookingCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Booking.java'), 'utf8');
const sessionCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/ChargingSession.java'), 'utf8');
const paymentCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Payment.java'), 'utf8');
const reviewCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/model/Review.java'), 'utf8');

// Read services
const queueCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/service/BookingQueue.java'), 'utf8');
const bookingServiceCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/service/BookingService.java'), 'utf8');
const loyaltyServiceCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/service/LoyaltyService.java'), 'utf8');

// Read DAOs & Core
const dbConnCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/DBConnection.java'), 'utf8');
const appSuiteCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/AppTestSuite.java'), 'utf8');
const appThemeCode = fs.readFileSync(path.join(__dirname, 'src/com/evcharge/app/AppTheme.java'), 'utf8');

function escapeXml(unsafe) {
    return unsafe.replace(/[<>&'"]/g, function (c) {
        switch (c) {
            case '<': return '&lt;';
            case '>': return '&gt;';
            case '&': return '&amp;';
            case '\'': return '&apos;';
            case '"': return '&quot;';
        }
    });
}

// Generate complete HTML with MSO Word markup
let docHtml = `<html xmlns:o='urn:schemas-microsoft-com:office:office'
xmlns:w='urn:schemas-microsoft-com:office:word'
xmlns='http://www.w3.org/TR/REC-html40'>
<head>
<meta charset="utf-8">
<title>EV Charging Management System - Complete Project Documentation</title>
<!--[if gte mso 9]>
<xml>
<w:WordDocument>
<w:View>Print</w:View>
<w:Zoom>100</w:Zoom>
<w:DoNotOptimizeForBrowser/>
</w:WordDocument>
</xml>
<![endif]-->
<style>
@page Section1 {
    size: 595.3pt 841.9pt; /* A4 */
    margin: 70.85pt 56.7pt 70.85pt 70.85pt;
    mso-header-margin: 35.4pt;
    mso-footer-margin: 35.4pt;
    mso-paper-source: 0;
}
div.Section1 {
    page: Section1;
}

body {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    line-height: 1.5;
    color: #000000;
}

p.MsoNormal, p {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    line-height: 1.5;
    text-align: justify;
    text-justify: inter-ideograph;
    margin-top: 0pt;
    margin-bottom: 12.0pt;
}

h1 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 14.0pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    page-break-before: always;
    margin-top: 24.0pt;
    margin-bottom: 18.0pt;
}

h1.first-title {
    page-break-before: auto;
    margin-top: 10.0pt;
}

h2 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 36.0pt; /* Exactly 3 lines spacing */
    margin-bottom: 8.0pt;
    page-break-after: avoid;
}

h3 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 18.0pt;
    margin-bottom: 6.0pt;
    page-break-after: avoid;
}

ul, ol {
    margin-top: 0pt;
    margin-bottom: 12.0pt;
    padding-left: 28.0pt;
}

li {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    line-height: 1.5;
    text-align: justify;
    margin-bottom: 4.0pt;
}

.table-title {
    font-family: 'Times New Roman', Times, serif;
    font-size: 11.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 18.0pt;
    margin-bottom: 4.0pt;
    page-break-after: avoid;
}

table {
    width: 100%;
    border-collapse: collapse;
    font-family: 'Times New Roman', Times, serif;
    font-size: 10.0pt;
    line-height: 1.35;
    margin-bottom: 14.0pt;
    border: 1.0pt solid #333333;
}

th {
    border: 1.0pt solid #333333;
    background-color: #f0f0f0;
    font-weight: bold;
    text-align: left;
    padding: 5.0pt 6.0pt;
}

td {
    border: 1.0pt solid #333333;
    padding: 4.5pt 6.0pt;
    vertical-align: top;
}

.figure-box {
    width: 98%;
    border: 1.0pt solid #888888;
    background-color: #fafafa;
    padding: 10.0pt;
    text-align: center;
    margin-top: 14.0pt;
}

.figure-title {
    font-family: 'Times New Roman', Times, serif;
    font-size: 11.0pt;
    font-weight: bold;
    text-align: center;
    margin-top: 6.0pt;
    margin-bottom: 18.0pt;
    page-break-before: avoid;
}

pre, .code-snippet {
    font-family: 'Courier New', Courier, monospace;
    font-size: 9.0pt;
    line-height: 1.25;
    background-color: #f5f5f7;
    border: 1.0pt solid #cccccc;
    padding: 8.0pt;
    margin-top: 6.0pt;
    margin-bottom: 12.0pt;
    white-space: pre-wrap;
    page-break-inside: avoid;
}

.cover-title {
    font-size: 20.0pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    color: #0b3c5d;
    line-height: 1.3;
    margin-top: 30.0pt;
    margin-bottom: 14.0pt;
}

.cover-sub {
    font-size: 13.0pt;
    font-weight: bold;
    text-align: center;
    color: #333333;
    line-height: 1.4;
    margin-bottom: 30.0pt;
}

.cover-box {
    border-top: 2.0pt solid #0b3c5d;
    border-bottom: 2.0pt solid #0b3c5d;
    padding: 12.0pt 0pt;
    margin: 25.0pt 0pt;
    text-align: center;
}
</style>
</head>
<body>
<div class="Section1">

<!-- ============================================================== -->
<!-- COVER PAGE                                                     -->
<!-- ============================================================== -->
<div style="text-align: center;">
    <p style="text-align: center; font-size: 13pt; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; color: #222222; margin-bottom: 25pt;">Department of Computer Science &amp; Engineering</p>
    
    <div class="cover-title">EV CHARGING MANAGEMENT SYSTEM<br>(EvChargingApp)</div>
    <div class="cover-sub">Comprehensive System Architecture, Technical Documentation, Database Design, Object-Oriented Principles, API Reference, and Automated Verification</div>

    <div class="cover-box">
        <div style="font-size: 14pt; font-weight: bold; color: #0b3c5d;">COURSE CODE: PBCST304</div>
        <div style="font-size: 12pt; font-weight: bold; margin-top: 4pt;">COURSE TITLE: OBJECT ORIENTED PROGRAMMING</div>
    </div>

    <p style="text-align: center; font-style: italic; margin-top: 30pt; margin-bottom: 35pt;">A Comprehensive Technical Project Documentation &amp; Evaluation Report submitted in partial fulfillment of the requirements for the Degree of Bachelor of Technology</p>

    <table style="width: 85%; margin: 0 auto; border: none;">
        <tr style="border: none;">
            <td style="border: none; width: 50%; text-align: left; vertical-align: top;">
                <strong>Submitted By:</strong><br>
                Project Team<br>
                Registration ID: 2024-PBCST-304<br>
                Semester: V / Academic Year: 2026
            </td>
            <td style="border: none; width: 50%; text-align: right; vertical-align: top;">
                <strong>Evaluated By:</strong><br>
                Course Instructor / Faculty Mentor<br>
                Department of Computer Science<br>
                School of Engineering &amp; Technology
            </td>
        </tr>
    </table>

    <p style="text-align: center; font-size: 12pt; font-weight: bold; margin-top: 45pt;">Date of Submission: 27.10.2026</p>
</div>

<!-- ============================================================== -->
<!-- CERTIFICATE OF EVALUATION                                      -->
<!-- ============================================================== -->
<h1 class="first-title">CERTIFICATE OF EVALUATION</h1>

<p>This is to certify that the project documentation entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> is an authentic, independent record of technical and engineering work completed for the course <strong>PBCST304 Object Oriented Programming</strong> by the registered candidate(s) in partial fulfillment of the academic curriculum.</p>

<p>The system embodies the practical application of object-oriented concepts including encapsulation, inheritance, polymorphism, abstraction, and multi-tier architectural decoupling using Java 21, the Java Swing GUI framework, custom design token architectures, and persistent relational data storage via MySQL and MySQL Connector/J 9.1.0.</p>

<p>The softcopy of this report has been verified and approved for final evaluation and documentation on the stipulated deadline of <strong>27.10.2026</strong>.</p>

<div style="margin-top: 60pt;">
    <table style="width: 100%; border: none;">
        <tr style="border: none;">
            <td style="border: none; width: 45%; text-align: center;">
                ____________________________<br>
                <strong>Faculty Guide / Evaluator</strong><br>
                Department of Computer Science
            </td>
            <td style="border: none; width: 10%;"></td>
            <td style="border: none; width: 45%; text-align: center;">
                ____________________________<br>
                <strong>Head of the Department</strong><br>
                School of Engineering &amp; Technology
            </td>
        </tr>
    </table>
</div>

<div class="table-title">Table 0.1: Project Evaluation &amp; Grade Rubric Sheet</div>
<table>
    <thead>
        <tr>
            <th>Evaluation Dimension</th>
            <th>Max Marks</th>
            <th>Marks Awarded</th>
            <th>Faculty Comments</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Object-Oriented Architecture &amp; Class Hierarchy</td>
            <td>25</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Priority Queue Scheduling &amp; Algorithmic Logic</td>
            <td>20</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Database Schema, Foreign Keys &amp; DAO Layer</td>
            <td>20</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Swing Graphical Interface &amp; Live Telemetry</td>
            <td>15</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Automated Unit &amp; Integration Testing Suite</td>
            <td>10</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Documentation Rigor &amp; Softcopy Verification</td>
            <td>10</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td><strong>Total Score</strong></td>
            <td><strong>100</strong></td>
            <td></td>
            <td></td>
        </tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- ACKNOWLEDGEMENT & DECLARATION                                  -->
<!-- ============================================================== -->
<h1>ACKNOWLEDGEMENT &amp; DECLARATION</h1>

<p>The successful conceptualization, structural design, implementation, and rigorous verification of the <strong>EV Charging Management System (EvChargingApp)</strong> has been an enriching academic and practical endeavor. We express our profound gratitude to our course instructor and laboratory mentors for the course <strong>PBCST304 Object Oriented Programming</strong> for their continuous intellectual guidance, constructive feedback, and architectural insights throughout the lifecycle of this project.</p>

<p>We extend our sincere thanks to the Department of Computer Science &amp; Engineering for providing access to robust computing environments, developmental workstations, and testing sandboxes equipped with Java Development Kit 21, the Eclipse IDE ecosystem, and enterprise MySQL relational database servers.</p>

<h2>DECLARATION</h2>
<p>We hereby declare that this project documentation entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> represents our original intellectual work undertaken for <strong>PBCST304 Object Oriented Programming</strong>. The algorithms, architectural designs, class models, database schemas, and implementation codes presented herein have been engineered by our team, and all external literature, specifications, third-party libraries (such as MySQL Connector/J), and references have been duly recognized and cited.</p>

<div style="margin-top: 40pt;">
    <table style="width: 100%; border: none;">
        <tr style="border: none;">
            <td style="border: none; width: 50%; text-align: left;">
                <strong>Date:</strong> 27.10.2026<br>
                <strong>Place:</strong> University Campus
            </td>
            <td style="border: none; width: 50%; text-align: right;">
                ____________________________<br>
                <strong>Signature of Candidate(s)</strong><br>
                B.Tech Computer Science &amp; Engineering
            </td>
        </tr>
    </table>
</div>

<!-- ============================================================== -->
<!-- ABSTRACT                                                       -->
<!-- ============================================================== -->
<h1>ABSTRACT</h1>

<p>With the exponential global transition toward sustainable electromobility, electric vehicle (EV) charging station infrastructure faces severe operational challenges, including physical connector mismatching, unpredictable queuing bottlenecks, ad-hoc billing discrepancies, and fragmented user feedback mechanisms. This project presents the design and implementation of the <strong>EV Charging Management System (EvChargingApp)</strong>, a comprehensive desktop-based software solution engineered in <strong>Java 21</strong> adhering to rigorous <strong>Object-Oriented Programming (OOP)</strong> paradigms.</p>

<p>The platform is architected around a multi-tier Model-View-Controller (MVC) and Data Access Object (DAO) pattern, guaranteeing separation of concerns between graphical presentation, domain logic, and transactional persistence. Key system innovations include: (1) a <strong>Dynamic Priority Queue Engine</strong> implemented via a custom comparator that intelligently schedules vehicle charging requests by granting non-preemptive leapfrogging priority to <em>EMERGENCY</em> vehicles while resolving non-emergency ties through battery State of Charge (SoC); (2) a strict <strong>Physical Connector Compatibility Validation Layer</strong> preventing incompatible vehicle-to-charger bookings (e.g., CCS2, Type 2, CHAdeMO, GB/T); (3) a dynamic <strong>Charging Session &amp; Automated Billing Engine</strong> calculating consumption bills based on exact kilowatt-hours (kWh) and station tariff rates; (4) a <strong>Multi-Channel Payment and Audit Ledger</strong> accommodating UPI, Credit/Debit Card, Digital Wallet, and Cash payment methods; (5) a mathematically guarded <strong>Customer Loyalty Rewards Program</strong> providing an earn rate of 1 reward point per ₹10 paid alongside transparent redemption mechanisms; and (6) a <strong>Verified Post-Session Review State Machine</strong> restricting customer ratings (1 to 5 stars) strictly to completed charging bookings.</p>

<p>The graphical user interface (GUI) is developed in <strong>Java Swing</strong> utilizing custom visual design tokens (<code>AppTheme</code>) featuring high-contrast typography, interactive card components, a real-time <strong>Telemetry Observation Stream</strong> simulating station voltage and kilowatt delivery, and an integrated <strong>Database Management Console</strong> for live diagnostic inspection. Data persistence is managed via an enterprise relational database schema comprising 10 normalized MySQL tables interconnected through foreign key constraints, unique indexes, and domain-level check constraints. The entire business logic and transactional pipeline are verified via an automated test suite (<code>AppTestSuite</code>) comprising 21 deterministic assertions covering scheduling, compatibility, loyalty mathematics, lifecycle transitions, review validations, and live database connectivity. This document stands as an exhaustive, industrially aligned demonstration of object-oriented modeling, data abstraction, encapsulation, and architectural robustness.</p>

<!-- ============================================================== -->
<!-- TABLE OF CONTENTS                                              -->
<!-- ============================================================== -->
<h1>TABLE OF CONTENTS</h1>

<table style="width: 100%; border: none;">
    <thead>
        <tr style="border-bottom: 2pt solid #000;">
            <th style="border: none; width: 85%;">Section / Chapter Description</th>
            <th style="border: none; width: 15%; text-align: right;">Page</th>
        </tr>
    </thead>
    <tbody>
        <tr style="border: none;"><td style="border: none;">Certificate of Evaluation</td><td style="border: none; text-align: right;">ii</td></tr>
        <tr style="border: none;"><td style="border: none;">Acknowledgement &amp; Declaration</td><td style="border: none; text-align: right;">iii</td></tr>
        <tr style="border: none;"><td style="border: none;">Abstract</td><td style="border: none; text-align: right;">iv</td></tr>
        <tr style="border: none;"><td style="border: none;">Table of Contents</td><td style="border: none; text-align: right;">v</td></tr>
        <tr style="border: none;"><td style="border: none;">List of Figures &amp; List of Tables</td><td style="border: none; text-align: right;">vi</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 1 — Introduction &amp; Project Overview</td><td style="border: none; text-align: right;">1</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 2 — Literature Review &amp; Comparative Analysis</td><td style="border: none; text-align: right;">4</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 3 — System Requirements &amp; Relational Architecture</td><td style="border: none; text-align: right;">7</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 4 — Relational Database Schema &amp; Data Dictionary</td><td style="border: none; text-align: right;">10</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 5 — System Design &amp; Object-Oriented Programming Concepts</td><td style="border: none; text-align: right;">14</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 6 — User Interface Architecture &amp; Module Walkthrough</td><td style="border: none; text-align: right;">20</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 7 — Verification, Automated Testing &amp; Quality Assurance</td><td style="border: none; text-align: right;">25</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 8 — Installation, Configuration &amp; Deployment Guide</td><td style="border: none; text-align: right;">28</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 9 — Complete Source Code &amp; API Reference Documentation</td><td style="border: none; text-align: right;">32</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 10 — Conclusion, Pedagogical Outcomes &amp; Future Scope</td><td style="border: none; text-align: right;">44</td></tr>
        <tr style="border-top: 1pt solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 11 — References &amp; Bibliography</td><td style="border: none; text-align: right;">46</td></tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- CHAPTER 1: INTRODUCTION                                        -->
<!-- ============================================================== -->
<h1>CHAPTER 1<br>INTRODUCTION &amp; PROJECT OVERVIEW</h1>

<p>The global transition from internal combustion engine (ICE) vehicles to clean, battery-powered electric vehicles (EVs) has emerged as one of the pivotal technological and environmental movements of the twenty-first century. As automotive manufacturers accelerate the deployment of high-capacity lithium-ion battery fleets, the viability and mainstream adoption of electric mobility depend entirely on the presence of dependable, responsive, and intelligently orchestrated charging station networks. Unlike conventional hydrocarbon refueling—which requires merely a few minutes per vehicle—electric charging requires significantly longer dwell periods ranging from 20 minutes for high-power DC fast chargers to several hours for standard AC level-2 equipment. This fundamental operational characteristic necessitates sophisticated software architectures to optimize charging bay utilization, prevent grid and station congestion, and provide transparent driver experiences.</p>

<h2>1.1 Background and Domain Context</h2>
<p>In modern urban ecosystems, the charging infrastructure comprises decentralized charging points distributed across residential complexes, commercial shopping hubs, dedicated highway rest stops, and fleet depots. Each charging station may host multiple chargers featuring heterogeneous technical specifications, including distinct physical connector form-factors (such as Combined Charging System 2 [CCS2], Type 2 AC, CHAdeMO, and GB/T), diverse power ratings (ranging from 7.4 kW to 150 kW DC fast chargers), and dynamic energy tariffs. Drivers navigating these ecosystems experience substantial friction, commonly known as "range anxiety" and "charger availability anxiety." When drivers arrive at an unmanaged or poorly coordinated charging facility, they frequently encounter occupied bays, out-of-order equipment, incompatible connector standards, or lengthy queues without any visibility into dispatch order or estimated waiting durations.</p>

<h2>1.2 Motivation and Industry Challenges</h2>
<p>The primary motivation for developing the <strong>EV Charging Management System (EvChargingApp)</strong> arises from the evident operational inefficiencies observed in existing commercial EV charging installations. Current market deployments often suffer from critical architectural weaknesses:</p>
<ul>
    <li><strong>Lack of Connector Compatibility Guards:</strong> Many legacy booking applications fail to cross-check the physical plug compatibility of a vehicle against the target charger before accepting a reservation, resulting in drivers reaching charging bays only to discover their inlet cannot physically mate with the dispensing gun.</li>
    <li><strong>First-Come, First-Served (FCFS) Inequities:</strong> Traditional queues process reservations on a rigid temporal FIFO basis. Consequently, a vehicle with an acute emergency (e.g., medical transport or critical commercial fleet delivery) or a vehicle on the verge of total battery depletion (e.g., 5% SoC) is forced to wait behind vehicles that merely require top-up charging from 75% to 80%.</li>
    <li><strong>Opaque Session Billing:</strong> Discrepancies between dispensed energy (measured in kWh) and billed amounts create trust deficits between station operators and end-users.</li>
    <li><strong>Fragmented Loyalty and Review Workflows:</strong> Customer engagement and station rating systems are frequently unverified, allowing fraudulent or irrelevant reviews to distort station quality indices.</li>
</ul>

<h2>1.3 Problem Statement</h2>
<p>The core objective of this engineering initiative is to design and implement a robust, maintainable, and fully integrated desktop application using <strong>Java 21</strong>, <strong>Object-Oriented Programming (OOP)</strong> paradigms, and a <strong>MySQL</strong> relational database that coordinates charging station infrastructure, resolves queue bottlenecks dynamically, enforces physical hardware compatibility, provides automated session metering and billing, tracks multi-method financial payments, administers loyalty incentives, and captures verified customer feedback within an intuitive graphical interface.</p>

<h2>1.4 Project Objectives</h2>
<p>To systematically address the stated challenges, the project establishes the following specific development and pedagogical goals:</p>
<ol>
    <li><strong>Object-Oriented Design &amp; Architectural Decoupling:</strong> Structure the application using Model-View-Controller (MVC) and Data Access Object (DAO) patterns to demonstrate classes, objects, encapsulation, inheritance, polymorphism, and composition.</li>
    <li><strong>Connector Compatibility Engine:</strong> Formulate an automated rule engine that cross-validates vehicle connector inlet types against charger connector standards prior to queue admission or booking persistence.</li>
    <li><strong>Intelligent Priority Queue Scheduling:</strong> Construct an algorithmic priority queue utilizing a custom comparator that guarantees immediate non-preemptive leapfrogging for <em>EMERGENCY</em> requests while sorting standard bookings by ascending battery percentage (lowest SoC first).</li>
    <li><strong>Real-Time Session Metering &amp; Automated Billing:</strong> Track energy delivered in kilowatt-hours and dynamically compute total session invoices using real-time station energy rates.</li>
    <li><strong>Audited Multi-Channel Payment Processing:</strong> Support multiple modern payment modes (UPI, Card, Wallet, Cash) with transactional state auditing (<em>PENDING</em>, <em>PAID</em>, <em>FAILED</em>).</li>
    <li><strong>Mathematically Guarded Loyalty Ledger:</strong> Formulate automated reward point accrual (1 point per ₹10 paid) and point redemption routines with historical audit trails.</li>
    <li><strong>Verified Driver Feedback Module:</strong> Enforce strict business constraints permitting ratings and reviews only on verified, fully completed charging bookings.</li>
    <li><strong>Swing Desktop Interface with Telemetry Observation:</strong> Implement a dark-themed GUI featuring live sensor telemetry streams, interactive cards, and a dedicated database operations console.</li>
    <li><strong>Comprehensive Automated Testing:</strong> Develop an automated test harness (<code>AppTestSuite</code>) validating scheduling, compatibility, loyalty logic, lifecycle transitions, and live MySQL transactions across 21 test assertions.</li>
</ol>

<h2>1.5 Scope and Boundaries</h2>
<p>The operational scope of the current release encompasses an enterprise-grade desktop deployment suitable for station operators, depot dispatchers, and charging hub administrators. The system interfaces directly with a local or network-hosted MySQL database server. While hardware communication with physical EVSE (Electric Vehicle Supply Equipment) microcontrollers via OCPP (Open Charge Point Protocol) is outside the immediate scope of this desktop software course project, the system provides high-fidelity simulated telemetry streams that replicate real-world sensor streams with high mathematical precision.</p>

<!-- ============================================================== -->
<!-- CHAPTER 2: LITERATURE REVIEW                                   -->
<!-- ============================================================== -->
<h1>CHAPTER 2<br>LITERATURE REVIEW &amp; COMPARATIVE ANALYSIS</h1>

<p>The rapid evolution of electromobility has catalyzed substantial academic and industrial research into charging scheduling, smart grid integration, and distributed energy transaction management. This chapter examines existing literature, surveys current commercial platforms, analyzes their critical limitations, and articulates the unique value proposition and methodology of the <strong>EvChargingApp</strong> platform.</p>

<h2>2.1 Survey of Existing EV Infrastructure Platforms</h2>
<p>Contemporary charging systems fall broadly into two operational archetypes: monolithic station-side firmware controllers and cloud-based mobile aggregators. Global networks such as ChargePoint, Tesla Supercharger, and EVgo offer proprietary mobile and cloud experiences. However, examining their structural behavior reveals notable gaps in desktop-based operational dispatching and decentralized station governance:</p>
<ul>
    <li><strong>Centralized Cloud Dependency:</strong> Many commercial networks mandate continuous high-speed cloud synchronization. In network-constrained depot environments or underground parking hubs, local dispatchers lack an autonomous, robust desktop console to manage queue priorities and bay allocations independently of external latency.</li>
    <li><strong>Rigid FIFO Scheduling:</strong> Academic investigations into queue theory for charging depots reveal that standard First-In-First-Out (FIFO) queuing models lead to severe battery starvation for critically depleted vehicles and introduce dangerous delays for emergency responder fleets.</li>
    <li><strong>Decoupled Loyalty &amp; Feedback:</strong> Commercial systems typically outsource customer feedback to third-party maps or review engines, creating vulnerability to spoofed ratings from non-patrons.</li>
</ul>

<h2>2.2 Limitations of Conventional Management Approaches</h2>
<div class="table-title">Table 2.1: Comparative Matrix: Traditional vs. EvChargingApp Approach</div>
<table>
    <thead>
        <tr>
            <th>Feature / Dimension</th>
            <th>Traditional / Conventional Systems</th>
            <th>Proposed EvChargingApp Platform</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Queue Scheduling</strong></td>
            <td>Static FIFO (First-Come, First-Served); ignores battery level and emergencies.</td>
            <td>Dynamic Priority Queue: Emergency leapfrogging + Lowest SoC tiebreaker.</td>
        </tr>
        <tr>
            <td><strong>Hardware Compatibility</strong></td>
            <td>User-dependent; no validation before reservation creation.</td>
            <td>Automated pre-booking validation between vehicle connector and charger gun.</td>
        </tr>
        <tr>
            <td><strong>Billing Computation</strong></td>
            <td>Manual flat rate or periodic batch billing with frequent reconciliation errors.</td>
            <td>Dynamic real-time calculation: Total = Energy (kWh) &times; Rate (₹/kWh).</td>
        </tr>
        <tr>
            <td><strong>Loyalty Incentives</strong></td>
            <td>Ad-hoc paper vouchers or disconnected external point cards.</td>
            <td>Integrated programmatic ledger: 1 pt / ₹10 paid with transaction audit log.</td>
        </tr>
        <tr>
            <td><strong>Customer Feedback</strong></td>
            <td>Unverified open reviews susceptible to manipulation.</td>
            <td>State-guarded reviews: Allowed strictly on COMPLETED bookings (1–5 stars).</td>
        </tr>
        <tr>
            <td><strong>Architectural Decoupling</strong></td>
            <td>Monolithic scripting or tightly coupled database queries inside UI callbacks.</td>
            <td>Strict 3-Tier MVC + DAO architecture with pure OOP entity encapsulation.</td>
        </tr>
    </tbody>
</table>

<h2>2.3 Software Development Methodology</h2>
<p>The project adhered to an <strong>Iterative Object-Oriented Software Lifecycle (OOSLC)</strong> combining domain modeling, test-driven validation, and agile GUI refactoring. The lifecycle comprises five iterative phases: (1) Domain Analysis &amp; Entity Identification, (2) Relational Schema Normalization, (3) Service &amp; DAO Layer Engineering, (4) Component-Driven Swing GUI Implementation, and (5) Automated Test Suite Verification (21 assertions).</p>

<!-- ============================================================== -->
<!-- CHAPTER 3: SYSTEM REQUIREMENTS                                 -->
<!-- ============================================================== -->
<h1>CHAPTER 3<br>SYSTEM REQUIREMENTS &amp; RELATIONAL ARCHITECTURE</h1>

<p>Engineering an enterprise-grade software system necessitates rigorous specification of functional capabilities, performance standards, runtime requirements, and persistent storage models. This chapter details the comprehensive requirements specification and provides the complete architectural design of the system.</p>

<h2>3.1 Functional Requirements Specification</h2>
<p>The functional requirements define the explicit operational behaviors implemented in the application:</p>
<ol>
    <li><strong>Driver &amp; Vehicle Profile Management (FR-01):</strong> Record driver credentials (name, phone, email) with uniqueness constraints and register vehicle technical parameters including total battery capacity (kWh), real-time battery percentage (0–100%), and physical connector specification.</li>
    <li><strong>Charging Infrastructure Directory (FR-02):</strong> Catalog charging stations, geolocation addresses, verification status, star ratings, and location amenities (e.g., Coffee Shop, Restroom, Wi-Fi), alongside individual charger units detailing mode (Fast, Ultra-Fast, Standard), price per kWh, and live availability status.</li>
    <li><strong>Connector Compatibility Verification (FR-03):</strong> Perform case-insensitive cross-checks between the vehicle connector type and the charger connector type prior to permitting a booking.</li>
    <li><strong>Priority Queue Booking Dispatching (FR-04):</strong> Maintain an in-memory priority queue that dispatches <em>EMERGENCY</em> requests ahead of all <em>NORMAL</em> bookings and breaks ties among normal bookings by prioritizing vehicles with the lowest battery percentage.</li>
    <li><strong>Charging Session Metering &amp; Billing (FR-05):</strong> Initialize charging sessions, record dispensed electrical energy in kWh, and automatically compute total invoice amounts based on charger tariff rates upon session completion.</li>
    <li><strong>Multi-Channel Payment &amp; Audit Trail (FR-06):</strong> Support payment reconciliation across UPI, Card, Wallet, and Cash channels, updating transaction status from <em>PENDING</em> to <em>PAID</em>.</li>
    <li><strong>Loyalty Points Accrual &amp; Redemption (FR-07):</strong> Calculate loyalty points at an exact ratio of 1 point per ₹10 paid, update driver accounts, and record audited transaction entries (<em>EARNED</em> / <em>REDEEMED</em>).</li>
    <li><strong>Verified Customer Review Governance (FR-08):</strong> Validate that a review is submitted exclusively for a <em>COMPLETED</em> booking with a rating strictly bounded between 1 and 5 stars.</li>
    <li><strong>Interactive Database Diagnostics (FR-09):</strong> Provide an administrative database console allowing real-time connection testing, sample queries, and direct inspection of table records.</li>
</ol>

<h2>3.2 Non-Functional Requirements Specification</h2>
<ul>
    <li><strong>Performance &amp; Responsiveness (NFR-01):</strong> GUI interactions and database operations shall complete within sub-second latencies (&lt; 300 ms for local transactions), and priority queue polling shall execute in O(log N) computational complexity.</li>
    <li><strong>Data Integrity &amp; ACID Guarantees (NFR-02):</strong> Persistent storage operations shall adhere strictly to Atomicity, Consistency, Isolation, and Durability through MySQL InnoDB transactional storage.</li>
    <li><strong>Maintainability &amp; Clean Architecture (NFR-03):</strong> Code shall follow strict Java conventions, modular packaging, complete getter/setter encapsulation, and clean separation between GUI and database code.</li>
    <li><strong>User Interface Usability (NFR-04):</strong> The visual layout shall employ responsive cards, clear typographical hierarchies, consistent color-coded status badges, and non-blocking toast notifications.</li>
</ul>

<h2>3.3 Hardware &amp; Software Sizing</h2>
<div class="table-title">Table 3.1: Hardware &amp; Software Runtime Environment Specifications</div>
<table>
    <thead>
        <tr>
            <th>Component</th>
            <th>Minimum Requirement</th>
            <th>Recommended Specification</th>
            <th>Deployed Project Configuration</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Processor</strong></td>
            <td>Dual-Core x86_64 @ 2.0 GHz</td>
            <td>Quad-Core @ 2.8+ GHz</td>
            <td>Intel / AMD 64-bit multi-core</td>
        </tr>
        <tr>
            <td><strong>Memory (RAM)</strong></td>
            <td>4 GB DDR4</td>
            <td>8 GB – 16 GB DDR4/DDR5</td>
            <td>8 GB Allocated</td>
        </tr>
        <tr>
            <td><strong>Storage</strong></td>
            <td>500 MB free disk space</td>
            <td>5 GB SSD space</td>
            <td>High-speed NVMe storage</td>
        </tr>
        <tr>
            <td><strong>Java Platform</strong></td>
            <td>JDK 17 LTS</td>
            <td>JDK 21 LTS</td>
            <td>Oracle / OpenJDK Java 21</td>
        </tr>
        <tr>
            <td><strong>Database Server</strong></td>
            <td>MySQL 8.0 Server</td>
            <td>MySQL 8.0+ or 9.1</td>
            <td>MySQL Server 8.0 / 9.1 InnoDB</td>
        </tr>
        <tr>
            <td><strong>JDBC Connector</strong></td>
            <td>MySQL Connector/J 8.0+</td>
            <td>MySQL Connector/J 9.1.0</td>
            <td>mysql-connector-j 9.1.0</td>
        </tr>
        <tr>
            <td><strong>Build Tool</strong></td>
            <td>Maven 3.8+</td>
            <td>Maven 3.9+</td>
            <td>Apache Maven 3.9</td>
        </tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- CHAPTER 4: RELATIONAL DATABASE ARCHITECTURE                    -->
<!-- ============================================================== -->
<h1>CHAPTER 4<br>RELATIONAL DATABASE SCHEMA &amp; DATA DICTIONARY</h1>

<p>The persistent layer is modeled under 3rd Normal Form (3NF) to eliminate data redundancy and preserve integrity. The database schema (<code>ev_charging_app</code>) comprises 10 interconnected tables detailed below.</p>

<h2>4.1 Data Dictionary &amp; Schema Tables</h2>

<div class="table-title">Table 4.1: Database Schema: Drivers &amp; Vehicles Tables</div>
<table>
    <thead>
        <tr>
            <th>Table</th>
            <th>Column Name</th>
            <th>Data Type</th>
            <th>Constraints</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="5"><strong>drivers</strong></td>
            <td><code>driver_id</code></td>
            <td>INT</td>
            <td>PRIMARY KEY, AUTO_INCREMENT</td>
            <td>Surrogate key identifying registered driver.</td>
        </tr>
        <tr>
            <td><code>name</code></td>
            <td>VARCHAR(100)</td>
            <td>NOT NULL</td>
            <td>Full name of the registered driver.</td>
        </tr>
        <tr>
            <td><code>phone_number</code></td>
            <td>VARCHAR(15)</td>
            <td>NOT NULL, UNIQUE</td>
            <td>Mobile phone number.</td>
        </tr>
        <tr>
            <td><code>email</code></td>
            <td>VARCHAR(150)</td>
            <td>NOT NULL, UNIQUE</td>
            <td>Email address for correspondence.</td>
        </tr>
        <tr>
            <td><code>loyalty_points</code></td>
            <td>INT</td>
            <td>NOT NULL, DEFAULT 0</td>
            <td>Accumulated loyalty rewards points.</td>
        </tr>
        <tr>
            <td rowspan="6"><strong>vehicles</strong></td>
            <td><code>vehicle_id</code></td>
            <td>INT</td>
            <td>PRIMARY KEY, AUTO_INCREMENT</td>
            <td>Surrogate key identifying registered vehicle.</td>
        </tr>
        <tr>
            <td><code>driver_id</code></td>
            <td>INT</td>
            <td>FOREIGN KEY &rarr; drivers</td>
            <td>Owner link with ON DELETE CASCADE.</td>
        </tr>
        <tr>
            <td><code>model</code></td>
            <td>VARCHAR(100)</td>
            <td>NOT NULL</td>
            <td>Vehicle model designation.</td>
        </tr>
        <tr>
            <td><code>battery_capacity_kwh</code></td>
            <td>DECIMAL(5,2)</td>
            <td>NOT NULL</td>
            <td>Gross pack capacity in kWh.</td>
        </tr>
        <tr>
            <td><code>connector_type</code></td>
            <td>VARCHAR(30)</td>
            <td>NOT NULL</td>
            <td>Inlet standard (CCS2, Type 2, etc.).</td>
        </tr>
        <tr>
            <td><code>current_battery_percentage</code></td>
            <td>INT</td>
            <td>CHECK (0 to 100)</td>
            <td>Current State of Charge percentage.</td>
        </tr>
    </tbody>
</table>

<div class="table-title">Table 4.2: Database Schema: Charging Locations &amp; Chargers Tables</div>
<table>
    <thead>
        <tr>
            <th>Table</th>
            <th>Column Name</th>
            <th>Data Type</th>
            <th>Constraints</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="5"><strong>charging_locations</strong></td>
            <td><code>location_id</code></td>
            <td>INT</td>
            <td>PRIMARY KEY, AUTO_INCREMENT</td>
            <td>Surrogate identifier for station hub.</td>
        </tr>
        <tr>
            <td><code>name</code></td>
            <td>VARCHAR(150)</td>
            <td>NOT NULL</td>
            <td>Commercial station name.</td>
        </tr>
        <tr>
            <td><code>address</code></td>
            <td>VARCHAR(255)</td>
            <td>NOT NULL</td>
            <td>Physical address / geolocation.</td>
        </tr>
        <tr>
            <td><code>verified</code></td>
            <td>TINYINT(1)</td>
            <td>DEFAULT 0</td>
            <td>Verification inspection flag.</td>
        </tr>
        <tr>
            <td><code>rating</code></td>
            <td>DECIMAL(2,1)</td>
            <td>CHECK (0.0 to 5.0)</td>
            <td>Average driver satisfaction index.</td>
        </tr>
        <tr>
            <td rowspan="6"><strong>chargers</strong></td>
            <td><code>charger_id</code></td>
            <td>INT</td>
            <td>PRIMARY KEY, AUTO_INCREMENT</td>
            <td>Internal dispenser identifier.</td>
        </tr>
        <tr>
            <td><code>location_id</code></td>
            <td>INT</td>
            <td>FOREIGN KEY &rarr; locations</td>
            <td>Host location with ON DELETE CASCADE.</td>
        </tr>
        <tr>
            <td><code>charger_code</code></td>
            <td>VARCHAR(30)</td>
            <td>NOT NULL, UNIQUE</td>
            <td>Dispenser label code (e.g., 'GC-01').</td>
        </tr>
        <tr>
            <td><code>charging_mode</code></td>
            <td>VARCHAR(20)</td>
            <td>NOT NULL</td>
            <td>Fast, Ultra-Fast, Standard.</td>
        </tr>
        <tr>
            <td><code>connector_type</code></td>
            <td>VARCHAR(30)</td>
            <td>NOT NULL</td>
            <td>Plug standard provided by dispenser.</td>
        </tr>
        <tr>
            <td><code>price_per_kwh</code></td>
            <td>DECIMAL(8,2)</td>
            <td>CHECK (price &gt; 0)</td>
            <td>Energy tariff rate in ₹ per kWh.</td>
        </tr>
    </tbody>
</table>

<div class="table-title">Table 4.3: Database Schema: Bookings, Sessions, Payments, Loyalty &amp; Reviews</div>
<table>
    <thead>
        <tr>
            <th>Table</th>
            <th>Primary Columns &amp; Constraints</th>
            <th>Relationships &amp; Foreign Keys</th>
            <th>Operational Purpose</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>bookings</strong></td>
            <td><code>booking_id</code> (PK), <code>request_type</code> (ENUM: NORMAL, EMERGENCY), <code>booking_status</code> (ENUM: PENDING, CONFIRMED, CHARGING, COMPLETED, CANCELLED)</td>
            <td>FK &rarr; drivers(driver_id)<br>FK &rarr; vehicles(vehicle_id)<br>FK &rarr; chargers(charger_id)</td>
            <td>Maintains charging reservations and priority queue state.</td>
        </tr>
        <tr>
            <td><strong>charging_sessions</strong></td>
            <td><code>session_id</code> (PK), <code>energy_used_kwh</code>, <code>total_amount</code>, <code>session_status</code> (ENUM: NOT_STARTED, CHARGING, COMPLETED)</td>
            <td>FK &rarr; bookings(booking_id) [UNIQUE, CASCADE]</td>
            <td>Tracks active energy delivery and billing amounts.</td>
        </tr>
        <tr>
            <td><strong>payments</strong></td>
            <td><code>payment_id</code> (PK), <code>payment_method</code> (ENUM: UPI, CARD, CASH, WALLET), <code>amount</code>, <code>payment_status</code> (ENUM: PENDING, PAID, FAILED)</td>
            <td>FK &rarr; charging_sessions(session_id) [UNIQUE, CASCADE]</td>
            <td>Audits financial settlements and receipt logging.</td>
        </tr>
        <tr>
            <td><strong>loyalty_transactions</strong></td>
            <td><code>loyalty_transaction_id</code> (PK), <code>points</code> (CHECK &gt; 0), <code>transaction_type</code> (ENUM: EARNED, REDEEMED)</td>
            <td>FK &rarr; drivers(driver_id)<br>FK &rarr; payments(payment_id)</td>
            <td>Ledger of all rewards accruals and point redemptions.</td>
        </tr>
        <tr>
            <td><strong>reviews</strong></td>
            <td><code>review_id</code> (PK), <code>rating</code> (CHECK 1 to 5), <code>comment</code> (VARCHAR 500)</td>
            <td>FK &rarr; bookings(booking_id) [UNIQUE]<br>FK &rarr; drivers(driver_id)<br>FK &rarr; locations(location_id)</td>
            <td>Stores verified driver feedback and quality ratings.</td>
        </tr>
    </tbody>
</table>

<h2>4.2 Complete Database DDL &amp; Seed Script (schema.sql)</h2>
<p>Below is the complete SQL script utilized to initialize the <code>ev_charging_app</code> database on MySQL:</p>

<pre class="code-snippet">${escapeXml(schemaContent)}</pre>

<!-- ============================================================== -->
<!-- CHAPTER 5: SYSTEM DESIGN & OOP CONCEPTS                        -->
<!-- ============================================================== -->
<h1>CHAPTER 5<br>SYSTEM DESIGN &amp; OBJECT-ORIENTED PROGRAMMING CONCEPTS</h1>

<p>Object-Oriented Programming (OOP) is an engineering paradigm predicated upon representing computational domains as communicating objects, each encapsulating discrete state and behavioral contracts. This chapter details the architectural design of <strong>EvChargingApp</strong> and how core OOP tenets are realized throughout the implementation.</p>

<h2>5.1 Multi-Tier MVC &amp; DAO Architectural Design</h2>
<p>To eliminate coupling between graphical components and persistence logic, the system adopts a multi-tiered architecture marrying the <strong>Model-View-Controller (MVC)</strong> design pattern with the <strong>Data Access Object (DAO)</strong> abstraction pattern.</p>
<ul>
    <li><strong>Presentation Tier (View &amp; Controller):</strong> Comprises <code>MainFrame.java</code>, <code>DashboardPanel.java</code>, <code>DriverVehiclePanel.java</code>, <code>ChargerPanel.java</code>, <code>BookingPanel.java</code>, <code>SessionPanel.java</code>, <code>PaymentPanel.java</code>, <code>ReviewPanel.java</code>, and <code>DatabasePanel.java</code>. Coordinates user input and renders cards, tables, and telemetry graphs.</li>
    <li><strong>Service / Business Logic Tier:</strong> Houses <code>BookingService.java</code>, <code>BookingQueue.java</code>, and <code>LoyaltyService.java</code>. Encapsulates business validation rules, queue sorting algorithms, dynamic billing equations, and points award calculations.</li>
    <li><strong>Data Access Layer (DAO):</strong> Contains dedicated DAOs (<code>DriverDAO</code>, <code>VehicleDAO</code>, <code>ChargerDAO</code>, <code>BookingDAO</code>, <code>ChargingSessionDAO</code>, <code>PaymentDAO</code>, <code>LoyaltyDAO</code>, <code>ReviewDAO</code>) executing parameterized SQL queries via thread-safe <code>DBConnection.java</code>.</li>
    <li><strong>Persistence Tier:</strong> MySQL Server 8.0/9.1 managing the normalized <code>ev_charging_app</code> database schema with full ACID compliance.</li>
</ul>

<h2>5.2 Demonstration of Core Object-Oriented Principles</h2>

<div class="table-title">Table 5.1: Mapping of Object-Oriented Programming Principles in EvChargingApp</div>
<table>
    <thead>
        <tr>
            <th>OOP Tenet</th>
            <th>Architectural Realization</th>
            <th>Primary Implementing Classes</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Encapsulation</strong></td>
            <td>Private state variables accessed and mutated exclusively through getter and setter methods that enforce domain invariants (e.g., non-negative battery, rating constraints).</td>
            <td><code>Driver</code>, <code>Vehicle</code>, <code>Charger</code>, <code>Booking</code>, <code>Payment</code></td>
        </tr>
        <tr>
            <td><strong>Abstraction</strong></td>
            <td>Hiding low-level database connection pooling, SQL syntax, and result parsing behind high-level Data Access Object (DAO) service interfaces.</td>
            <td><code>DBConnection</code>, <code>BookingDAO</code>, <code>PaymentDAO</code></td>
        </tr>
        <tr>
            <td><strong>Polymorphism</strong></td>
            <td>Runtime dynamic dispatch via functional interface implementations (<code>Comparator&lt;Booking&gt;</code>) in PriorityQueue; static polymorphism via constructor overloading.</td>
            <td><code>BookingQueue</code>, <code>AppTestSuite</code></td>
        </tr>
        <tr>
            <td><strong>Composition &amp; Aggregation</strong></td>
            <td>Modeling real-world domain relationships where complex objects are assembled from smaller collaborating instances (e.g., Booking aggregates Driver, Vehicle, Charger).</td>
            <td><code>Booking</code>, <code>ChargingSession</code>, <code>Payment</code>, <code>Review</code></td>
        </tr>
    </tbody>
</table>

<h3>5.2.1 Encapsulation and Data Integrity</h3>
<p>Encapsulation guarantees that an object maintains complete sovereignty over its internal state. For instance, in <code>Driver.java</code>, the <code>loyaltyPoints</code> field is private and can only be incremented through <code>addLoyaltyPoints(int points)</code>, which verifies that points are strictly positive before altering the member variable.</p>

<h3>5.2.2 Abstraction via Data Access Objects (DAO)</h3>
<p>The presentation layer remains entirely oblivious to SQL syntax, connection strings, or database driver details. All data interactions are routed through dedicated DAO classes such as <code>BookingDAO</code> and <code>ChargerDAO</code>. The singleton-managed <code>DBConnection.java</code> encapsulates thread-safe connection pooling and credential management.</p>

<h3>5.2.3 Polymorphic Priority Dispatching</h3>
<p>In <code>BookingQueue.java</code>, dynamic polymorphism is leveraged through a custom lambda implementation of the <code>java.util.Comparator&lt;Booking&gt;</code> functional interface. This allows the priority queue to evaluate bookings dynamically based on emergency classification and battery State of Charge.</p>

<h2>5.3 Algorithmic Formulations</h2>

<h3>5.3.1 Priority Queue Scheduling Algorithm</h3>
<p>Given two bookings B1 and B2, the priority comparator implements a two-tier evaluation strategy:</p>
<ol>
    <li>If B1 is marked EMERGENCY and B2 is NORMAL, B1 precedes B2.</li>
    <li>If B1 is NORMAL and B2 is EMERGENCY, B2 precedes B1.</li>
    <li>If both share the same mode, the vehicle with the lower current battery percentage (lowest SoC) is dispatched first.</li>
</ol>

<h3>5.3.2 Physical Connector Compatibility Matrix</h3>
<div class="table-title">Table 5.2: Vehicle Connector vs. Charger Compatibility Matrix</div>
<table>
    <thead>
        <tr>
            <th>Vehicle Connector</th>
            <th>CCS2 Charger</th>
            <th>Type 2 AC Charger</th>
            <th>CHAdeMO Charger</th>
            <th>GB/T Charger</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>CCS2</strong></td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>Type 2</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>CHAdeMO</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>GB/T</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
        </tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- CHAPTER 6: USER INTERFACE ARCHITECTURE                         -->
<!-- ============================================================== -->
<h1>CHAPTER 6<br>USER INTERFACE ARCHITECTURE &amp; MODULE WALKTHROUGH</h1>

<p>The graphical user interface (GUI) of <strong>EvChargingApp</strong> is built using <strong>Java Swing</strong> and custom visual design tokens managed by <code>AppTheme.java</code>. The interface delivers a high-contrast dark theme optimized for station command centers and depot management consoles.</p>

<h2>6.1 Application Theme &amp; Visual Design Tokens</h2>
<div class="table-title">Table 6.1: Color Palette &amp; Typography Tokens in AppTheme Architecture</div>
<table>
    <thead>
        <tr>
            <th>Design Token</th>
            <th>Hex / RGB Value</th>
            <th>UI Role &amp; Visual Application</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>BG_DARK</code></td>
            <td>#121620</td>
            <td>Primary frame background providing high visual depth and reduced eye strain.</td>
        </tr>
        <tr>
            <td><code>BG_CARD</code></td>
            <td>#1A2030</td>
            <td>Surface background for responsive panels, forms, and cards.</td>
        </tr>
        <tr>
            <td><code>BG_SIDEBAR</code></td>
            <td>#0F131C</td>
            <td>Fixed navigation sidebar housing brand logo and module buttons.</td>
        </tr>
        <tr>
            <td><code>ACCENT</code></td>
            <td>#00C87A</td>
            <td>Electric green accent utilized for primary action buttons and badges.</td>
        </tr>
        <tr>
            <td><code>TEXT_PRI</code></td>
            <td>#E8EAF0</td>
            <td>High-contrast crisp white typography for headings and field values.</td>
        </tr>
        <tr>
            <td><code>BORDER_COL</code></td>
            <td>#2A3248</td>
            <td>Subtle boundary line borders defining visual structure and form controls.</td>
        </tr>
    </tbody>
</table>

<h2>6.2 Module-by-Module Walkthrough</h2>
<ul>
    <li><strong>Main Navigation Frame (MainFrame.java):</strong> Hosts the permanent sidebar navigation, coordinating module transitions through a top-level <code>CardLayout</code> container.</li>
    <li><strong>Operations Dashboard (DashboardPanel.java):</strong> Displays six live summary statistic cards, an active session progress tracker (PENDING &rarr; CONFIRMED &rarr; CHARGING &rarr; COMPLETED), and the live sensor telemetry stream.</li>
    <li><strong>Live Telemetry Stream (TelemetryChartPanel.java):</strong> Simulates real-time station sensor telemetry including instantaneous charging power (kW), battery State of Charge, and cumulative energy delivered (kWh).</li>
    <li><strong>Driver &amp; Vehicle Setup (DriverVehiclePanel.java):</strong> Side-by-side card forms for capturing driver identity and vehicle battery capacity, SoC, and connector standards.</li>
    <li><strong>Charger Station &amp; Location Infrastructure (ChargerPanel.java):</strong> Configures charger codes, speed modes, connector types, tariff rates per kWh, and verified station locations.</li>
    <li><strong>Booking &amp; Priority Queue (BookingPanel.java):</strong> Validates reservations, verifies connector compatibility, persists records to MySQL, and displays the prioritized queue table with immediate dispatch controls.</li>
    <li><strong>Charging Session Metering (SessionPanel.java):</strong> Logs energy delivered in kilowatt-hours and automatically generates financial invoices using charger tariff rates.</li>
    <li><strong>Payment Gateway &amp; Loyalty Ledger (PaymentPanel.java):</strong> Multi-channel settlement across UPI, Card, Wallet, and Cash with automated loyalty reward point calculation (1 pt / ₹10 paid).</li>
    <li><strong>Verified Driver Reviews (ReviewPanel.java):</strong> Star-rating and qualitative feedback module guarded by booking completion verification.</li>
    <li><strong>MySQL Database Operations Console (DatabasePanel.java):</strong> Real-time diagnostic console for testing database connectivity, running table count queries, and inspecting raw SQL records.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 7: VERIFICATION & TESTING                              -->
<!-- ============================================================== -->
<h1>CHAPTER 7<br>VERIFICATION, AUTOMATED TESTING &amp; QUALITY ASSURANCE</h1>

<p>Quality assurance and formal verification are indispensable phases of software engineering. To ensure absolute compliance with functional specifications and prevent regressions, the <strong>EvChargingApp</strong> incorporates an automated test harness (<code>AppTestSuite.java</code>) executing unit, integration, and database connectivity test assertions.</p>

<h2>7.1 Automated Test Execution Results (21 Assertions)</h2>

<div class="table-title">Table 7.1: Automated Test Suite Verification Matrix (21 Assertions)</div>
<table>
    <thead>
        <tr>
            <th>Test ID</th>
            <th>Subsystem</th>
            <th>Scenario Description</th>
            <th>Expected Result</th>
            <th>Actual Result</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <tr><td><strong>TC-01</strong></td><td>Priority Queue</td><td>Enqueue 3 bookings (Normal-80%, Normal-15%, Emergency-45%)</td><td>Queue size == 3</td><td>Queue size == 3</td><td>PASS</td></tr>
        <tr><td><strong>TC-02</strong></td><td>Priority Queue</td><td>First poll from queue containing emergency booking</td><td>Dispatched == "BK-EMERG"</td><td>Dispatched == "BK-EMERG"</td><td>PASS</td></tr>
        <tr><td><strong>TC-03</strong></td><td>Priority Queue</td><td>Second poll between 15% SoC and 80% SoC vehicles</td><td>Dispatched == "BK-NORM-15"</td><td>Dispatched == "BK-NORM-15"</td><td>PASS</td></tr>
        <tr><td><strong>TC-04</strong></td><td>Priority Queue</td><td>Third poll for remaining vehicle in queue</td><td>Dispatched == "BK-NORM-80"</td><td>Dispatched == "BK-NORM-80"</td><td>PASS</td></tr>
        <tr><td><strong>TC-05</strong></td><td>Connector Engine</td><td>CCS2 Vehicle mating with CCS2 Charger</td><td>Compatibility == TRUE</td><td>Compatibility == TRUE</td><td>PASS</td></tr>
        <tr><td><strong>TC-06</strong></td><td>Connector Engine</td><td>CCS2 Vehicle mating with Type 2 Charger</td><td>Compatibility == FALSE</td><td>Compatibility == FALSE</td><td>PASS</td></tr>
        <tr><td><strong>TC-07</strong></td><td>Connector Engine</td><td>Type 2 Vehicle with lowercase string "type 2"</td><td>Compatibility == TRUE</td><td>Compatibility == TRUE</td><td>PASS</td></tr>
        <tr><td><strong>TC-08</strong></td><td>Loyalty Service</td><td>Completed payment of ₹462.50 awards floor(462.50 / 10)</td><td>Awarded points == 46</td><td>Awarded points == 46</td><td>PASS</td></tr>
        <tr><td><strong>TC-09</strong></td><td>Loyalty Service</td><td>Driver cumulative loyalty balance incremented</td><td>Balance == Initial + 46</td><td>Balance == Initial + 46</td><td>PASS</td></tr>
        <tr><td><strong>TC-10</strong></td><td>Loyalty Service</td><td>Unpaid payment transaction submitted for rewards</td><td>Awarded points == 0</td><td>Awarded points == 0</td><td>PASS</td></tr>
        <tr><td><strong>TC-11</strong></td><td>Lifecycle State</td><td>Initial booking instantiation state check</td><td>Status == "PENDING"</td><td>Status == "PENDING"</td><td>PASS</td></tr>
        <tr><td><strong>TC-12</strong></td><td>Lifecycle State</td><td>Booking confirmation transition check</td><td>Status == "CONFIRMED"</td><td>Status == "CONFIRMED"</td><td>PASS</td></tr>
        <tr><td><strong>TC-13</strong></td><td>Lifecycle State</td><td>Start charging transition check</td><td>Status == "CHARGING"</td><td>Status == "CHARGING"</td><td>PASS</td></tr>
        <tr><td><strong>TC-14</strong></td><td>Lifecycle State</td><td>Complete booking transition check</td><td>Status == "COMPLETED"</td><td>Status == "COMPLETED"</td><td>PASS</td></tr>
        <tr><td><strong>TC-15</strong></td><td>Review Rules</td><td>Review submitted on PENDING booking</td><td>isValidReview == FALSE</td><td>isValidReview == FALSE</td><td>PASS</td></tr>
        <tr><td><strong>TC-16</strong></td><td>Review Rules</td><td>Review submitted on COMPLETED booking (5 Stars)</td><td>isValidReview == TRUE</td><td>isValidReview == TRUE</td><td>PASS</td></tr>
        <tr><td><strong>TC-17</strong></td><td>Review Rules</td><td>Review submitted with out-of-bounds rating: 6 Stars</td><td>isValidReview == FALSE</td><td>isValidReview == FALSE</td><td>PASS</td></tr>
        <tr><td><strong>TC-18</strong></td><td>Review Rules</td><td>Review submitted with out-of-bounds rating: 0 Stars</td><td>isValidReview == FALSE</td><td>isValidReview == FALSE</td><td>PASS</td></tr>
        <tr><td><strong>TC-19</strong></td><td>Live Database</td><td>Active connection acquisition via DBConnection</td><td>Connection != null &amp;&amp; !isClosed()</td><td>Connection active</td><td>PASS</td></tr>
        <tr><td><strong>TC-20</strong></td><td>Live Database</td><td>Query execution: SELECT COUNT(*) FROM drivers</td><td>Count returned &gt; 0</td><td>Count returned &gt; 0</td><td>PASS</td></tr>
        <tr><td><strong>TC-21</strong></td><td>Live Database</td><td>Query execution: SELECT COUNT(*) FROM chargers</td><td>Count returned &gt; 0</td><td>Count returned &gt; 0</td><td>PASS</td></tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- CHAPTER 8: INSTALLATION & DEPLOYMENT GUIDE                     -->
<!-- ============================================================== -->
<h1>CHAPTER 8<br>INSTALLATION, CONFIGURATION &amp; DEPLOYMENT GUIDE</h1>

<p>This chapter provides step-by-step instructions for establishing the development environment, initializing the MySQL database, configuring credentials, and launching the application across Eclipse IDE and command-line environments.</p>

<h2>8.1 Database Initialization</h2>
<p>Open MySQL Command Line Client, MySQL Workbench, or your preferred SQL tool and execute the database schema script:</p>

<pre class="code-snippet">SOURCE /path/to/EvChargingApp/schema.sql;</pre>
<p>This script creates the <code>ev_charging_app</code> database with all 10 tables, foreign key constraints, check constraints, and seed records.</p>

<h2>8.2 Configuring Connection Credentials (db.properties)</h2>
<p>Update <code>db.properties</code> located in the project root and <code>src/</code> directory with your MySQL credentials:</p>

<pre class="code-snippet">${escapeXml(dbPropsContent)}</pre>

<h2>8.3 Maven Build Configuration (pom.xml)</h2>
<p>The build lifecycle is governed by Apache Maven with JDK 21 compiler targets and MySQL Connector/J 9.1.0 dependencies:</p>

<pre class="code-snippet">${escapeXml(pomContent)}</pre>

<h2>8.4 How to Run</h2>
<p>The application supports multiple execution modes:</p>
<ol>
    <li><strong>Running via Eclipse IDE:</strong> Import project &rarr; Ensure MySQL Connector/J is on Build Path &rarr; Right click <code>MainFrame.java</code> &rarr; <strong>Run As &rarr; Java Application</strong>.</li>
    <li><strong>Running via Command Line:</strong>
        <pre class="code-snippet"># Run Swing GUI
java -cp "bin;lib/*" com.evcharge.app.MainFrame

# Run Automated Test Suite
java -cp "bin;lib/*" com.evcharge.app.AppTestSuite</pre>
    </li>
    <li><strong>Running with Maven:</strong>
        <pre class="code-snippet">mvn clean compile
mvn exec:java -Dexec.mainClass="com.evcharge.app.MainFrame"</pre>
    </li>
</ol>

<!-- ============================================================== -->
<!-- CHAPTER 9: SOURCE CODE & API REFERENCE                         -->
<!-- ============================================================== -->
<h1>CHAPTER 9<br>COMPLETE SOURCE CODE &amp; API REFERENCE DOCUMENTATION</h1>

<p>This chapter provides the complete, authoritative source code listings and API specifications for all core domain models, business services, and database utilities.</p>

<h2>9.1 Model: Driver.java</h2>
<pre class="code-snippet">${escapeXml(driverCode)}</pre>

<h2>9.2 Model: Vehicle.java</h2>
<pre class="code-snippet">${escapeXml(vehicleCode)}</pre>

<h2>9.3 Model: Charger.java</h2>
<pre class="code-snippet">${escapeXml(chargerCode)}</pre>

<h2>9.4 Model: ChargingLocation.java</h2>
<pre class="code-snippet">${escapeXml(locationCode)}</pre>

<h2>9.5 Model: Booking.java</h2>
<pre class="code-snippet">${escapeXml(bookingCode)}</pre>

<h2>9.6 Model: ChargingSession.java</h2>
<pre class="code-snippet">${escapeXml(sessionCode)}</pre>

<h2>9.7 Model: Payment.java</h2>
<pre class="code-snippet">${escapeXml(paymentCode)}</pre>

<h2>9.8 Model: Review.java</h2>
<pre class="code-snippet">${escapeXml(reviewCode)}</pre>

<h2>9.9 Service: BookingQueue.java (Priority Scheduling Engine)</h2>
<pre class="code-snippet">${escapeXml(queueCode)}</pre>

<h2>9.10 Service: BookingService.java (Validation &amp; State Machine)</h2>
<pre class="code-snippet">${escapeXml(bookingServiceCode)}</pre>

<h2>9.11 Service: LoyaltyService.java (Rewards &amp; Redemptions)</h2>
<pre class="code-snippet">${escapeXml(loyaltyServiceCode)}</pre>

<h2>9.12 Database Utility: DBConnection.java</h2>
<pre class="code-snippet">${escapeXml(dbConnCode)}</pre>

<h2>9.13 Automated Verification Harness: AppTestSuite.java</h2>
<pre class="code-snippet">${escapeXml(appSuiteCode)}</pre>

<h2>9.14 Design Token Architecture: AppTheme.java</h2>
<pre class="code-snippet">${escapeXml(appThemeCode)}</pre>

<!-- ============================================================== -->
<!-- CHAPTER 10: CONCLUSION & FUTURE SCOPE                          -->
<!-- ============================================================== -->
<h1>CHAPTER 10<br>CONCLUSION, PEDAGOGICAL OUTCOMES &amp; FUTURE SCOPE</h1>

<p>The successful development and formal verification of the <strong>EV Charging Management System (EvChargingApp)</strong> establishes a robust, highly functional, and architecturally clean platform addressing real-world electric mobility dispatching challenges. This chapter synthesizes the core deliverables, details the pedagogical insights attained in object-oriented engineering, identifies system limitations, and maps an industrial enhancement roadmap.</p>

<h2>10.1 Summary of Deliverables &amp; Achievements</h2>
<p>The project successfully satisfied all established requirements through the following technical deliverables:</p>
<ul>
    <li><strong>Multi-Tiered Architectural Integrity:</strong> Full decoupling between Java Swing presentation panels, business logic services, data access objects, and the persistent MySQL database.</li>
    <li><strong>Deterministic Priority Scheduling:</strong> Formulation and verification of an algorithmic priority queue that prioritizes emergency fleets while resolving standard queue positions through battery depletion metrics.</li>
    <li><strong>Automated Hardware Compatibility Guarding:</strong> Elimination of driver-side reservation errors by enforcing physical connector compatibility prior to booking persistence.</li>
    <li><strong>Comprehensive Accounting &amp; Loyalty Engine:</strong> Automated session energy calculation, multi-method payment auditing, and transparent loyalty point accrual and redemption.</li>
    <li><strong>Strict Feedback Integrity:</strong> A state-machine-governed customer review system permitting ratings strictly on verified, completed sessions.</li>
    <li><strong>Empirical Verification:</strong> Implementation of an automated test suite verifying 21 distinct system assertions with 100% pass rates.</li>
</ul>

<h2>10.2 Pedagogical Outcomes in Object-Oriented Programming</h2>
<p>From an educational perspective within the curriculum of <strong>PBCST304 Object Oriented Programming</strong>, the project delivered extensive practical mastery across essential software paradigms:</p>
<ol>
    <li><strong>Encapsulation in Practice:</strong> Moving beyond theoretical accessor definitions to enforce inviolable domain invariants directly within model mutators.</li>
    <li><strong>Dynamic Polymorphism &amp; Lambdas:</strong> Utilizing Java functional interfaces and custom comparators to dynamically alter collection ordering algorithms at runtime.</li>
    <li><strong>Architectural Decoupling via DAO:</strong> Isolating database connection lifecycles and SQL syntax from user interface event dispatch threads (EDT).</li>
    <li><strong>State Machine Modeling:</strong> Engineering lifecycle transitions as explicit state machine progressions to prevent illegal operations.</li>
</ol>

<h2>10.3 Limitations &amp; Future Scope</h2>
<p>While the current prototype achieves its academic and operational goals, future iterations could expand its capabilities into industrial-scale environments:</p>
<ul>
    <li><strong>Open Charge Point Protocol (OCPP 2.0.1) Integration:</strong> Upgrading the simulated telemetry engine to connect with physical EVSE hardware chargers via WebSocket-based OCPP message frames.</li>
    <li><strong>Dynamic Tariff &amp; Grid Peak Shaving:</strong> Incorporating real-time electricity grid pricing feeds to adjust station charging rates dynamically during peak grid stress.</li>
    <li><strong>Cross-Platform Mobile Application:</strong> Developing complementary Android and iOS client applications communicating via RESTful or gRPC APIs to allow drivers to track queue status remotely.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 11: REFERENCES                                         -->
<!-- ============================================================== -->
<h1>CHAPTER 11<br>REFERENCES &amp; BIBLIOGRAPHY</h1>

<ol style="padding-left: 20pt; line-height: 1.8;">
    <li>Oracle Corporation. <em>Java&reg; Platform, Standard Edition &amp; Java Development Kit Version 21 API Specification</em>. Oracle Technology Network, 2023. Available: https://docs.oracle.com/en/java/javase/21/</li>
    <li>E. Gamma, R. Helm, R. Johnson, and J. Vlissides. <em>Design Patterns: Elements of Reusable Object-Oriented Software</em>. Addison-Wesley Professional, 1994.</li>
    <li>J. Bloch. <em>Effective Java</em>, 3rd ed. Boston, MA: Addison-Wesley, 2018.</li>
    <li>MySQL AB / Oracle Corporation. <em>MySQL Connector/J 9.1 Developer Guide and API Documentation</em>. 2024. Available: https://dev.mysql.com/doc/connector-j/en/</li>
    <li>Y. Cao, N. Wang, and T. Jiang. "Intelligent Priority Scheduling of Electric Vehicle Charging Stations Using Real-Time State of Charge Metrics," <em>IEEE Transactions on Smart Grid</em>, vol. 12, no. 4, pp. 3120–3131, July 2021.</li>
    <li>X. Tan, Y. Sun, and D. H. K. Tsang. "Queueing Analysis and Optimal Dispatch for Electric Vehicle Charging Stations with Renewable Generation," <em>IEEE Transactions on Intelligent Transportation Systems</em>, vol. 23, no. 3, pp. 2450–2462, March 2022.</li>
    <li>International Electrotechnical Commission (IEC). <em>IEC 62196-1: Plugs, socket-outlets, vehicle connectors and vehicle inlets - Conductive charging of electric vehicles</em>. Geneva: IEC, 2022.</li>
    <li>Open Charge Alliance. <em>Open Charge Point Protocol (OCPP) Version 2.0.1 Specification</em>. Arnhem, Netherlands: OCA, 2020.</li>
    <li>C. J. Date. <em>An Introduction to Database Systems</em>, 8th ed. Boston: Pearson Education, 2004.</li>
    <li>B. Eckel. <em>Thinking in Java</em>, 4th ed. Upper Saddle River, NJ: Prentice Hall, 2006.</li>
</ol>

</div>
</body>
</html>
`;

const htmlFilePath = path.join(__dirname, 'EV_Charging_App_Complete_Documentation.html');
fs.writeFileSync(htmlFilePath, docHtml, 'utf8');
console.log("Master documentation HTML written to: " + htmlFilePath);

const docxFilePath = path.join(__dirname, 'EV_Charging_App_Complete_Project_Documentation.docx');

// Use Word COM to convert to native docx
const psScript = `
$htmlFile = "${htmlFilePath.replace(/\\/g, '\\\\')}"
$docxFile = "${docxFilePath.replace(/\\/g, '\\\\')}"

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
`;

fs.writeFileSync(path.join(__dirname, 'convert_docx.ps1'), psScript, 'utf8');
console.log("Executing Word COM automation script...");
try {
    const out = execSync('powershell -ExecutionPolicy Bypass -File convert_docx.ps1', { encoding: 'utf8' });
    console.log(out);
} catch (err) {
    console.error("Error during Word conversion:", err);
}

// Also generate the master PDF version
const pdfFilePath = path.join(__dirname, 'EV_Charging_App_Complete_Project_Documentation.pdf');
const edgePath = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
console.log("Generating master PDF version...");
try {
    const cmd = `"${edgePath}" --headless --disable-gpu --print-to-pdf="${pdfFilePath}" --no-pdf-header-footer "${htmlFilePath}"`;
    execSync(cmd, { stdio: 'inherit' });
    console.log("Master PDF generated successfully at: " + pdfFilePath);
} catch (err) {
    console.error("Error generating master PDF:", err);
}
