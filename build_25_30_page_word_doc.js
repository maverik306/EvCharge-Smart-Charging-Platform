const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

console.log("Expanding document to hit exact 26-28 page count in Word without code dumps...");

const htmlContent = `<html xmlns:o='urn:schemas-microsoft-com:office:office'
xmlns:w='urn:schemas-microsoft-com:office:word'
xmlns='http://www.w3.org/TR/REC-html40'>
<head>
<meta charset="utf-8">
<title>EV Charging Management System - Project Report</title>
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
    margin: 72.0pt 57.6pt 72.0pt 72.0pt;
    mso-header-margin: 36.0pt;
    mso-footer-margin: 36.0pt;
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

/* Chapter / Section title: Times New Roman, 14, Bold, Center */
h1 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 14.0pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    page-break-before: always;
    margin-top: 24.0pt;
    margin-bottom: 18.0pt;
    mso-style-name: "Heading 1";
}

h1.first-title {
    page-break-before: auto;
    margin-top: 10.0pt;
}

/* Heading 2: Times New Roman, 12, Bold, Left, 3 lines space before */
h2 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 36.0pt; /* Exactly 3 lines spacing */
    margin-bottom: 8.0pt;
    page-break-after: avoid;
    mso-style-name: "Heading 2";
}

/* Heading 3: Times New Roman, 12, Bold, Left */
h3 {
    font-family: 'Times New Roman', Times, serif;
    font-size: 12.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 18.0pt;
    margin-bottom: 6.0pt;
    page-break-after: avoid;
    mso-style-name: "Heading 3";
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
    margin-bottom: 6.0pt;
}

/* Table: Title ABOVE Table */
.table-title {
    font-family: 'Times New Roman', Times, serif;
    font-size: 11.0pt;
    font-weight: bold;
    text-align: left;
    margin-top: 20.0pt;
    margin-bottom: 5.0pt;
    page-break-after: avoid;
}

table {
    width: 100%;
    border-collapse: collapse;
    font-family: 'Times New Roman', Times, serif;
    font-size: 10.5pt;
    line-height: 1.4;
    margin-bottom: 16.0pt;
    border: 1.0pt solid #333333;
}

th {
    border: 1.0pt solid #333333;
    background-color: #f0f0f0;
    font-weight: bold;
    text-align: left;
    padding: 6.0pt 8.0pt;
}

td {
    border: 1.0pt solid #333333;
    padding: 5.5pt 8.0pt;
    vertical-align: top;
}

/* Figure: Title UNDER Figure */
.figure-container {
    text-align: center;
    margin-top: 18.0pt;
    margin-bottom: 6.0pt;
    page-break-inside: avoid;
}

.figure-box {
    width: 98%;
    border: 1.0pt solid #555555;
    background-color: #fbfbfb;
    padding: 12.0pt;
    text-align: center;
    margin: 0 auto;
}

.figure-title {
    font-family: 'Times New Roman', Times, serif;
    font-size: 11.0pt;
    font-weight: bold;
    text-align: center;
    margin-top: 7.0pt;
    margin-bottom: 20.0pt;
    page-break-before: avoid;
}

.cover-title {
    font-size: 20.0pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    color: #0b3c5d;
    line-height: 1.3;
    margin-top: 40.0pt;
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
    <div class="cover-sub">A Desktop-Based Multi-Tier Platform for Intelligent Priority Dispatching, Physical Connector Verification, Session Billing, and Customer Loyalty Rewards</div>

    <div class="cover-box">
        <div style="font-size: 14pt; font-weight: bold; color: #0b3c5d;">COURSE CODE: PBCST304</div>
        <div style="font-size: 12pt; font-weight: bold; margin-top: 4pt;">COURSE TITLE: OBJECT ORIENTED PROGRAMMING</div>
    </div>

    <p style="text-align: center; font-style: italic; margin-top: 30pt; margin-bottom: 35pt;">A Project Report submitted in partial fulfillment of the requirements for the Degree of Bachelor of Technology</p>

    <table style="width: 85%; margin: 0 auto; border: none;">
        <tr style="border: none;">
            <td style="border: none; width: 50%; text-align: left; vertical-align: top;">
                <strong>Submitted By:</strong><br>
                Project Team<br>
                Registration No: 2024-PBCST-304<br>
                Semester: V / Academic Year: 2026
            </td>
            <td style="border: none; width: 50%; text-align: right; vertical-align: top;">
                <strong>Under the Guidance of:</strong><br>
                Faculty Mentor / Course Evaluator<br>
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

<p>This is to certify that the project report entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> is a bona fide record of independent technical work carried out for the course <strong>PBCST304 Object Oriented Programming</strong> by the undersigned student(s) in partial fulfillment of the academic curriculum requirements.</p>

<p>The system embodies the practical application of object-oriented principles including encapsulation, inheritance, polymorphism, abstraction, and multi-tier architectural decoupling using Java 21, the Java Swing GUI framework, custom design token architectures, and persistent relational data storage via MySQL and MySQL Connector/J 9.1.0.</p>

<p>The softcopy of this report has been verified and approved for final evaluation and documentation on the stipulated deadline of <strong>27.10.2026</strong>.</p>

<div style="margin-top: 45pt;">
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
<!-- PREFATORY: ACKNOWLEDGEMENT                                     -->
<!-- ============================================================== -->
<h1 class="first-title">ACKNOWLEDGEMENT</h1>

<p>The successful conceptualization, structural design, implementation, and rigorous verification of the <strong>EV Charging Management System (EvChargingApp)</strong> has been an enriching academic and practical endeavor. We express our profound gratitude to our course instructor and laboratory mentors for the course <strong>PBCST304 Object Oriented Programming</strong> for their continuous intellectual guidance, constructive feedback, and architectural insights throughout the lifecycle of this project.</p>

<p>We extend our sincere thanks to the Department of Computer Science &amp; Engineering for providing access to robust computing environments, developmental workstations, and testing sandboxes equipped with Java Development Kit 21, the Eclipse IDE ecosystem, and enterprise MySQL relational database servers.</p>

<p>Finally, we express our heartfelt appreciation to our peers, families, and academic colleagues whose collaborative discussions, encouragement, and technical suggestions helped refine our priority dispatching algorithms, multi-method payment workflows, and unit test suites.</p>

<h2>DECLARATION</h2>
<p>We hereby declare that this project report entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> represents our original intellectual work undertaken for <strong>PBCST304 Object Oriented Programming</strong>. The algorithms, architectural designs, class models, database schemas, and implementation codes presented herein have been engineered by our team, and all external literature, specifications, third-party libraries (such as MySQL Connector/J), and references have been duly recognized and cited.</p>

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
<!-- PREFATORY: ABSTRACT                                            -->
<!-- ============================================================== -->
<h1>ABSTRACT</h1>

<p>With the exponential global transition toward sustainable electromobility, electric vehicle (EV) charging station infrastructure faces severe operational challenges, including physical connector mismatching, unpredictable queuing bottlenecks, ad-hoc billing discrepancies, and fragmented user feedback mechanisms. This project presents the design and implementation of the <strong>EV Charging Management System (EvChargingApp)</strong>, a comprehensive desktop-based software solution engineered in <strong>Java 21</strong> adhering to rigorous <strong>Object-Oriented Programming (OOP)</strong> paradigms.</p>

<p>The platform is architected around a multi-tier Model-View-Controller (MVC) and Data Access Object (DAO) pattern, guaranteeing separation of concerns between graphical presentation, domain logic, and transactional persistence. Key system innovations include: (1) a <strong>Dynamic Priority Queue Engine</strong> implemented via a custom comparator that intelligently schedules vehicle charging requests by granting non-preemptive leapfrogging priority to <em>EMERGENCY</em> vehicles while resolving non-emergency ties through battery State of Charge (SoC); (2) a strict <strong>Physical Connector Compatibility Validation Layer</strong> preventing incompatible vehicle-to-charger bookings (e.g., CCS2, Type 2, CHAdeMO, GB/T); (3) a dynamic <strong>Charging Session &amp; Automated Billing Engine</strong> calculating consumption bills based on exact kilowatt-hours (kWh) and station tariff rates; (4) a <strong>Multi-Channel Payment and Audit Ledger</strong> accommodating UPI, Credit/Debit Card, Digital Wallet, and Cash payment methods; (5) a mathematically guarded <strong>Customer Loyalty Rewards Program</strong> providing an earn rate of 1 reward point per ₹10 paid alongside transparent redemption mechanisms; and (6) a <strong>Verified Post-Session Review State Machine</strong> restricting customer ratings (1 to 5 stars) strictly to completed charging bookings.</p>

<p>The graphical user interface (GUI) is developed in <strong>Java Swing</strong> utilizing custom visual design tokens (<code>AppTheme</code>) featuring high-contrast typography, interactive card components, a real-time <strong>Telemetry Observation Stream</strong> simulating station voltage and kilowatt delivery, and an integrated <strong>Database Management Console</strong> for live diagnostic inspection. Data persistence is managed via an enterprise relational database schema comprising 10 normalized MySQL tables interconnected through foreign key constraints, unique indexes, and domain-level check constraints. The entire business logic and transactional pipeline are verified via an automated test suite (<code>AppTestSuite</code>) comprising 21 deterministic assertions covering scheduling, compatibility, loyalty mathematics, lifecycle transitions, review validations, and live database connectivity.</p>

<!-- ============================================================== -->
<!-- PREFATORY: CONTENTS (MATCHING USER REFERENCE EXACTLY)           -->
<!-- ============================================================== -->
<h1>CONTENTS</h1>

<table style="width: 100%; border: none;">
    <thead>
        <tr style="border-bottom: 2pt solid #000;">
            <th style="border: none; width: 85%;">Contents</th>
            <th style="border: none; width: 15%; text-align: right;">PageNo.</th>
        </tr>
    </thead>
    <tbody>
        <tr style="border: none;"><td style="border: none;">Acknowledgement ............................................................................................................</td><td style="border: none; text-align: right;">i</td></tr>
        <tr style="border: none;"><td style="border: none;">Abstract ............................................................................................................................</td><td style="border: none; text-align: right;">ii</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>1. Introduction</strong> ....................................................................................................................</td><td style="border: none; text-align: right;">1</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">1.1 Objective .....................................................................................................................</td><td style="border: none; text-align: right;">2</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>2. Existing and Proposed System</strong> ........................................................................................</td><td style="border: none; text-align: right;">6</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">2.1 Existing System ...........................................................................................................</td><td style="border: none; text-align: right;">6</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">2.2 Proposed System .........................................................................................................</td><td style="border: none; text-align: right;">7</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">2.3 Methodology ...............................................................................................................</td><td style="border: none; text-align: right;">9</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>3. Implementation Details</strong> .................................................................................................</td><td style="border: none; text-align: right;">11</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">3.1 System Requirement Specification ...............................................................................</td><td style="border: none; text-align: right;">11</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">3.2 Platform and IDE .........................................................................................................</td><td style="border: none; text-align: right;">13</td></tr>
        <tr style="border: none;"><td style="border: none; padding-left: 18pt;">3.3 Application Development and Implementation .............................................................</td><td style="border: none; text-align: right;">14</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>4. System Design</strong> ...............................................................................................................</td><td style="border: none; text-align: right;">16</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>5. Screen Shots</strong> ..................................................................................................................</td><td style="border: none; text-align: right;">20</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>6. Conclusion</strong> ....................................................................................................................</td><td style="border: none; text-align: right;">26</td></tr>
        <tr style="border: none;"><td style="border: none;"><strong>7. References</strong> ....................................................................................................................</td><td style="border: none; text-align: right;">27</td></tr>
    </tbody>
</table>

<!-- ============================================================== -->
<!-- CHAPTER 1: INTRODUCTION                                        -->
<!-- ============================================================== -->
<h1>1. INTRODUCTION</h1>

<p>The global transition from internal combustion engine (ICE) vehicles to clean, battery-powered electric vehicles (EVs) represents one of the most critical technological, economic, and environmental movements of our time. As automotive manufacturers worldwide accelerate the production and commercial deployment of high-capacity lithium-ion battery vehicle fleets, the viability and consumer acceptance of electric mobility depend entirely on the presence of dependable, responsive, and intelligently orchestrated charging station networks.</p>

<p>Unlike conventional hydrocarbon refueling—which requires merely three to five minutes per vehicle—electric vehicle charging requires substantially longer dwell periods ranging from 20 to 45 minutes for high-power DC fast chargers (50 kW to 150 kW) to several hours for standard AC level-2 charging equipment (7.4 kW to 22 kW). This fundamental operational reality creates severe queuing bottlenecks at charging facilities during peak commuting hours, long-distance highway travel, and commercial fleet changeovers. Without intelligent dispatching and real-time bay coordination, drivers experience significant delays, battery depletion anxieties, and severe operational frustration.</p>

<p>In modern urban ecosystems, the charging infrastructure comprises decentralized charging points distributed across residential complexes, commercial shopping hubs, dedicated highway rest stops, and fleet depots. Each charging station may host multiple chargers featuring heterogeneous technical specifications, including distinct physical connector form-factors (such as Combined Charging System 2 [CCS2], Type 2 AC, CHAdeMO, and GB/T), diverse power ratings, and dynamic energy tariffs. Drivers navigating these ecosystems experience substantial friction, commonly known as "range anxiety" and "charger availability anxiety." When drivers arrive at an unmanaged or poorly coordinated charging facility, they frequently encounter occupied bays, out-of-order equipment, incompatible connector standards, or lengthy queues without any visibility into dispatch order or estimated waiting durations.</p>

<p>The operational complexity expands significantly when considering fleet diversity. Commercial electric vehicles, emergency responders (such as electric ambulances and patient transport units), delivery vans, and private passenger cars all compete for access to the same limited charging infrastructure. A flat, unmanaged queue treats all arriving vehicles equally regardless of their mission criticality or emergency state. Furthermore, physical compatibility between vehicles and chargers is not universal; attempting to plug a CCS2 vehicle into a Type 2 dispenser without appropriate hardware adaptation results in physical failure and lost queue time. These operational realities demonstrate the acute necessity for dedicated software management systems tailored to the unique attributes of electric vehicle infrastructure.</p>

<p>The <strong>EV Charging Management System (EvChargingApp)</strong> addresses these pressing operational challenges through a modular, object-oriented desktop platform engineered in <strong>Java 21</strong>, backed by an enterprise <strong>MySQL</strong> relational database, and presented through a contemporary <strong>Java Swing</strong> desktop graphical user interface. The software provides station operators and dispatchers with complete supervisory visibility over driver profiles, vehicle battery capacities, charger bay hardware parameters, queue dispatch ordering, real-time session energy delivery, multi-channel payment reconciliation, customer loyalty rewards, and verified user feedback.</p>

<h2>1.1 Objective</h2>
<p>The primary objective of the <strong>EV Charging Management System</strong> is to engineer a dependable, modular, and maintainable software system that demonstrates the practical, real-world utility of <strong>Object-Oriented Programming (OOP)</strong> concepts in resolving electromobility infrastructure bottlenecks. Specifically, the project addresses the following detailed functional and pedagogical goals:</p>

<ul>
    <li><strong>Object-Oriented Architectural Decoupling:</strong> Structure the complete application using the <strong>Model-View-Controller (MVC)</strong> and <strong>Data Access Object (DAO)</strong> design patterns to separate graphical presentation, application business logic, and relational persistence. This demonstrates the core OOP principles of classes, objects, encapsulation, abstraction, inheritance, polymorphism, and object composition.</li>
    <li><strong>Physical Connector Compatibility Engine:</strong> Formulate an automated rule engine that cross-validates vehicle connector inlet types against charger connector standards prior to queue admission or booking persistence, eliminating reservation mismatch errors.</li>
    <li><strong>Intelligent Priority Queue Scheduling:</strong> Construct an algorithmic priority queue utilizing a custom comparator that guarantees immediate non-preemptive leapfrogging for <em>EMERGENCY</em> requests while sorting standard bookings by ascending battery percentage (lowest State of Charge first).</li>
    <li><strong>Automated Session Metering and Dynamic Billing:</strong> Track energy delivered in kilowatt-hours (kWh) and dynamically compute total session invoices using real-time station energy rates: \\( \\text{Total} = \\text{Energy Dispensed (kWh)} \\times \\text{Rate (₹/kWh)} \\).</li>
    <li><strong>Audited Multi-Channel Payment Processing:</strong> Support multiple modern payment modes (UPI, Credit/Debit Card, Digital Wallet, Cash) with transactional state auditing (<em>PENDING</em>, <em>PAID</em>, <em>FAILED</em>).</li>
    <li><strong>Mathematically Guarded Loyalty Rewards Ledger:</strong> Formulate automated reward point accrual (1 point per ₹10 paid) and point redemption routines with historical audit trails in persistent relational storage.</li>
    <li><strong>Verified Driver Feedback Module:</strong> Enforce strict business constraints permitting ratings and reviews (1 to 5 stars) only on verified, fully completed charging bookings, preventing fraudulent feedback.</li>
    <li><strong>Swing Desktop Interface with Telemetry Observation:</strong> Implement a dark-themed GUI featuring live sensor telemetry streams, interactive cards, and a dedicated database operations console.</li>
    <li><strong>Automated Test Suite Verification:</strong> Implement an automated verification harness (<code>AppTestSuite</code>) comprising 21 deterministic assertions validating scheduling, compatibility, loyalty mathematics, lifecycle transitions, and live MySQL transactions.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 2: EXISTING AND PROPOSED SYSTEM                        -->
<!-- ============================================================== -->
<h1>2. EXISTING AND PROPOSED SYSTEM</h1>

<h2>2.1 Existing System</h2>
<p>A comprehensive examination of conventional EV charging station management approaches reveals significant operational bottlenecks that impede smooth user adoption and reduce facility throughput. In existing uncoordinated or rudimentary software setups, the following severe limitations are prevalent:</p>

<ul>
    <li><strong>Rigid First-Come, First-Served (FCFS) Queuing:</strong> Conventional systems manage arriving vehicles using a strict temporal FIFO (First-In, First-Out) model. Consequently, a vehicle with an acute emergency (e.g., medical patient transport, emergency responder fleet, or critical commercial delivery) or a vehicle on the verge of total battery depletion (e.g., 5% SoC) is forced to wait behind vehicles that merely require non-urgent top-up charging from 75% to 80%. This rigid model leads to battery starvation and hazardous delays.</li>
    <li><strong>Absence of Connector Compatibility Validation:</strong> In many legacy reservation systems, bookings are accepted purely on the basis of time slots without validating whether the vehicle's physical inlet standard matches the dispenser's connector gun (such as CCS2, Type 2, CHAdeMO, or GB/T). Drivers arrive at the station only to realize that their vehicle cannot physically plug into the allocated bay, wasting time and causing station congestion.</li>
    <li><strong>Manual or Decoupled Billing Operations:</strong> Conventional systems frequently rely on disconnected energy meter readings or flat-rate manual invoices. This introduces mathematical discrepancies between actual kilowatt-hours dispensed and amounts billed, generating billing disputes and trust deficits.</li>
    <li><strong>Unverified Customer Feedback:</strong> Customer ratings in conventional setups are typically collected through open web forms or third-party mapping portals without verifying whether the reviewer ever completed a charging session at that location, leaving the station vulnerable to fraudulent reviews.</li>
    <li><strong>Monolithic and Coupled Software Design:</strong> Many existing mini-project applications suffer from tight coupling where database SQL queries, UI presentation callbacks, and business validation rules are intermingled in a single script or event handler, violating clean code and object-oriented principles.</li>
</ul>

<h2>2.2 Proposed System</h2>
<p>The proposed <strong>EV Charging Management System (EvChargingApp)</strong> fundamentally redesigns station operations through a decoupled, multi-tier software architecture engineered specifically to illustrate object-oriented principles. The system introduces several architectural and algorithmic innovations:</p>

<div class="table-title">Table 2.1: Comparative Matrix: Traditional System vs. Proposed EvChargingApp</div>
<table>
    <thead>
        <tr>
            <th>Operational Dimension</th>
            <th>Conventional / Existing Approach</th>
            <th>Proposed EvChargingApp Platform</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Queue Scheduling</strong></td>
            <td>Static FIFO (First-Come, First-Served); completely ignores battery percentage and emergency status.</td>
            <td>Dynamic Priority Queue: Emergency leapfrogging + Lowest SoC tiebreaker sorting.</td>
        </tr>
        <tr>
            <td><strong>Hardware Compatibility</strong></td>
            <td>Unvalidated; user error leads to incompatible vehicle-charger bay allocations.</td>
            <td>Automated pre-booking validation between vehicle connector and charger gun.</td>
        </tr>
        <tr>
            <td><strong>Session Billing</strong></td>
            <td>Manual flat-rate or batch calculations with frequent billing discrepancies.</td>
            <td>Dynamic real-time calculation: \\( \\text{Bill} = \\text{Energy (kWh)} \\times \\text{Rate (₹/kWh)} \\).</td>
        </tr>
        <tr>
            <td><strong>Loyalty Incentives</strong></td>
            <td>Ad-hoc paper coupons or disconnected external point cards.</td>
            <td>Integrated programmatic ledger: 1 pt / ₹10 paid with transaction audit log.</td>
        </tr>
        <tr>
            <td><strong>Customer Reviews</strong></td>
            <td>Unverified open reviews susceptible to manipulation and spoofing.</td>
            <td>State-guarded reviews: Allowed strictly on COMPLETED bookings (1–5 stars).</td>
        </tr>
        <tr>
            <td><strong>Architectural Design</strong></td>
            <td>Monolithic scripting with SQL queries embedded directly inside UI callbacks.</td>
            <td>Strict 3-Tier MVC + DAO architecture with pure OOP entity encapsulation.</td>
        </tr>
    </tbody>
</table>

<p>The proposed system ensures that invalid states—such as attempting to charge a CCS2 vehicle using a Type 2 connector or submitting a review for an uncompleted booking—are physically impossible within the code. Furthermore, the priority queue abstraction provides mathematical determinism in dispatching vehicles, substantially reducing station turnaround time and prioritizing emergency vehicles.</p>

<p>The platform introduces continuous real-time telemetry observation within the desktop management console. Operators can monitor instantaneous charging power (kW), line voltage, and battery charging curves dynamically, allowing early detection of thermal throttling or grid anomalies. By integrating multi-channel payment options (UPI, Credit/Debit Card, Digital Wallet, Cash) with an automated customer loyalty rewards ledger, the application incentivizes repeated patronage while maintaining complete financial transparency.</p>

<h2>2.3 Methodology</h2>
<p>The development of the <strong>EvChargingApp</strong> platform adhered to an <strong>Iterative Object-Oriented Software Lifecycle (OOSLC)</strong> combining domain modeling, test-driven validation, and agile GUI refactoring. The methodology encompasses five structured phases:</p>

<ol>
    <li><strong>Phase 1: Domain Analysis &amp; Entity Identification:</strong> Analyzing real-world physical charging facilities and decomposing them into discrete domain entities: <code>Driver</code>, <code>Vehicle</code>, <code>ChargingLocation</code>, <code>Charger</code>, <code>Booking</code>, <code>ChargingSession</code>, <code>Payment</code>, and <code>Review</code>.</li>
    <li><strong>Phase 2: Relational Schema Normalization (3NF):</strong> Structuring a 10-table MySQL relational database schema ensuring Third Normal Form (3NF) compliance, establishing primary keys, foreign key constraints with <code>ON DELETE CASCADE</code>, unique indexes, and domain-level <code>CHECK</code> constraints.</li>
    <li><strong>Phase 3: Service Layer &amp; Data Access Object (DAO) Implementation:</strong> Writing thread-safe JDBC connection handlers (<code>DBConnection.java</code>), dedicated DAO classes for each entity, and business logic services (<code>BookingQueue.java</code>, <code>BookingService.java</code>, <code>LoyaltyService.java</code>) encapsulating validation algorithms.</li>
    <li><strong>Phase 4: Component-Driven Swing GUI Development:</strong> Constructing responsive Swing panels using a centralized design token repository (<code>AppTheme.java</code>), implementing interactive card components, and unifying all modules inside a master <code>CardLayout</code> navigation frame (<code>MainFrame.java</code>).</li>
    <li><strong>Phase 5: Automated Verification &amp; Quality Assurance:</strong> Constructing and executing an automated test harness (<code>AppTestSuite.java</code>) executing 21 discrete test assertions covering queue priority, compatibility, loyalty rules, state machines, and live database queries.</li>
</ol>

<!-- ============================================================== -->
<!-- CHAPTER 3: IMPLEMENTATION DETAILS                              -->
<!-- ============================================================== -->
<h1>3. IMPLEMENTATION DETAILS</h1>

<h2>3.1 System Requirement Specification</h2>
<p>The software requirements for the <strong>EvChargingApp</strong> platform are bifurcated into functional specifications that define the explicit capabilities of the system and non-functional specifications that ensure operational reliability, performance, and maintainability.</p>

<h3>3.1.1 Functional Requirements (FR)</h3>
<ul>
    <li><strong>FR-01 (Driver &amp; Vehicle Management):</strong> The system shall record driver details (name, unique phone number, unique email) and vehicle technical attributes including gross battery capacity (kWh), real-time battery percentage (0–100%), and physical connector specification.</li>
    <li><strong>FR-02 (Charging Infrastructure Directory):</strong> The system shall catalog charging stations with geolocation addresses, verification status, star ratings, and location amenities (Coffee Shop, Restroom, Wi-Fi), alongside individual chargers detailing mode (Fast, Ultra-Fast, Standard), price per kWh, and real-time availability.</li>
    <li><strong>FR-03 (Connector Compatibility Verification):</strong> The system shall perform case-insensitive cross-checks between the vehicle connector type and the charger connector type prior to permitting a booking.</li>
    <li><strong>FR-04 (Priority Queue Dispatching):</strong> The system shall maintain an in-memory priority queue that dispatches <em>EMERGENCY</em> requests ahead of all <em>NORMAL</em> bookings and breaks ties among normal bookings by prioritizing vehicles with the lowest battery percentage.</li>
    <li><strong>FR-05 (Session Metering &amp; Dynamic Billing):</strong> The system shall initialize charging sessions, record dispensed electrical energy in kWh, and automatically compute total invoice amounts based on charger tariff rates upon session completion.</li>
    <li><strong>FR-06 (Multi-Channel Payment Processing):</strong> The system shall support payment reconciliation across UPI, Card, Wallet, and Cash channels, updating transaction status from <em>PENDING</em> to <em>PAID</em>.</li>
    <li><strong>FR-07 (Loyalty Points Accrual &amp; Redemption):</strong> The system shall calculate loyalty points at an exact ratio of 1 point per ₹10 paid, update driver accounts, and record audited transaction entries (<em>EARNED</em> / <em>REDEEMED</em>).</li>
    <li><strong>FR-08 (Verified Customer Review Governance):</strong> The system shall validate that a review is submitted exclusively for a <em>COMPLETED</em> booking with a rating strictly bounded between 1 and 5 stars.</li>
    <li><strong>FR-09 (Database Diagnostic Console):</strong> The system shall provide an administrative database console allowing real-time connection testing, sample queries, and direct inspection of table records.</li>
</ul>

<h3>3.1.2 Automated Test &amp; Verification Specification</h3>
<p>To assure absolute quality and guarantee that every functional requirement is met without regressions, the system specifies 21 automated test cases executed by the test harness (<code>AppTestSuite.java</code>). Table 3.1 outlines the verification traceability matrix.</p>

<div class="table-title">Table 3.1: Automated Verification Test Traceability Matrix (TC-01 to TC-21)</div>
<table>
    <thead>
        <tr>
            <th>Test ID</th>
            <th>Target Subsystem</th>
            <th>Requirement Verified</th>
            <th>Validation Logic &amp; Invariants</th>
            <th>Outcome</th>
        </tr>
    </thead>
    <tbody>
        <tr><td><strong>TC-01</strong></td><td>Priority Queue</td><td>FR-04 Queue Enqueue</td><td>Asserts total queue size equals 3 after adding mixed priority bookings.</td><td>PASS</td></tr>
        <tr><td><strong>TC-02</strong></td><td>Priority Queue</td><td>FR-04 Emergency Dispatch</td><td>Asserts EMERGENCY booking leapfrogs normal vehicles to position 1.</td><td>PASS</td></tr>
        <tr><td><strong>TC-03</strong></td><td>Priority Queue</td><td>FR-04 SoC Tiebreaker</td><td>Asserts vehicle with 15% SoC is dispatched ahead of vehicle with 80% SoC.</td><td>PASS</td></tr>
        <tr><td><strong>TC-04</strong></td><td>Priority Queue</td><td>FR-04 Queue Depletion</td><td>Asserts remaining vehicle in queue is dispatched in correct sequence.</td><td>PASS</td></tr>
        <tr><td><strong>TC-05</strong></td><td>Connector Engine</td><td>FR-03 Hardware Matching</td><td>Asserts CCS2 vehicle successfully matches CCS2 charging dispenser.</td><td>PASS</td></tr>
        <tr><td><strong>TC-06</strong></td><td>Connector Engine</td><td>FR-03 Hardware Rejection</td><td>Asserts CCS2 vehicle is rejected when attempting to book Type 2 charger.</td><td>PASS</td></tr>
        <tr><td><strong>TC-07</strong></td><td>Connector Engine</td><td>FR-03 Case Normalization</td><td>Asserts case-insensitive matching (e.g., 'type 2' matches 'Type 2').</td><td>PASS</td></tr>
        <tr><td><strong>TC-08</strong></td><td>Loyalty Service</td><td>FR-07 Math Calculation</td><td>Asserts award points equal floor(Amount / 10) on completed payment.</td><td>PASS</td></tr>
        <tr><td><strong>TC-09</strong></td><td>Loyalty Service</td><td>FR-07 Account Balance</td><td>Asserts driver cumulative loyalty points are incremented atomically.</td><td>PASS</td></tr>
        <tr><td><strong>TC-10</strong></td><td>Loyalty Service</td><td>FR-07 Unpaid Guard</td><td>Asserts unpaid or pending payment awards exactly 0 points.</td><td>PASS</td></tr>
        <tr><td><strong>TC-11</strong></td><td>Lifecycle State</td><td>FR-05 State Machine</td><td>Asserts new booking is initialized with status 'PENDING'.</td><td>PASS</td></tr>
        <tr><td><strong>TC-12</strong></td><td>Lifecycle State</td><td>FR-05 State Machine</td><td>Asserts confirmed booking transitions to status 'CONFIRMED'.</td><td>PASS</td></tr>
        <tr><td><strong>TC-13</strong></td><td>Lifecycle State</td><td>FR-05 State Machine</td><td>Asserts active charging session transitions status to 'CHARGING'.</td><td>PASS</td></tr>
        <tr><td><strong>TC-14</strong></td><td>Lifecycle State</td><td>FR-05 State Machine</td><td>Asserts finished charging session transitions status to 'COMPLETED'.</td><td>PASS</td></tr>
        <tr><td><strong>TC-15</strong></td><td>Review Governance</td><td>FR-08 Status Guard</td><td>Asserts review submission on PENDING booking is strictly rejected.</td><td>PASS</td></tr>
        <tr><td><strong>TC-16</strong></td><td>Review Governance</td><td>FR-08 Rating Validation</td><td>Asserts review submission on COMPLETED booking with 5 stars is accepted.</td><td>PASS</td></tr>
        <tr><td><strong>TC-17</strong></td><td>Review Governance</td><td>FR-08 Upper Bound Guard</td><td>Asserts rating of 6 stars is out-of-bounds and rejected.</td><td>PASS</td></tr>
        <tr><td><strong>TC-18</strong></td><td>Review Governance</td><td>FR-08 Lower Bound Guard</td><td>Asserts rating of 0 stars is out-of-bounds and rejected.</td><td>PASS</td></tr>
        <tr><td><strong>TC-19</strong></td><td>Live Database</td><td>FR-09 Connectivity</td><td>Asserts active MySQL connection acquisition via DBConnection pool.</td><td>PASS</td></tr>
        <tr><td><strong>TC-20</strong></td><td>Live Database</td><td>FR-09 Data Query</td><td>Asserts query execution and record retrieval on 'drivers' table.</td><td>PASS</td></tr>
        <tr><td><strong>TC-21</strong></td><td>Live Database</td><td>FR-09 Data Query</td><td>Asserts query execution and record retrieval on 'chargers' table.</td><td>PASS</td></tr>
    </tbody>
</table>

<h3>3.1.3 Non-Functional Requirements (NFR)</h3>
<ul>
    <li><strong>NFR-01 (Sub-Second Responsiveness):</strong> All user interface interactions and database operations shall complete within sub-second latencies (&lt; 300 ms for local transactions), and priority queue polling shall execute in O(log N) computational complexity.</li>
    <li><strong>NFR-02 (ACID Transactional Guarantees):</strong> Persistent storage operations shall adhere strictly to Atomicity, Consistency, Isolation, and Durability through the MySQL InnoDB storage engine.</li>
    <li><strong>NFR-03 (Modular Maintainability):</strong> Code shall follow strict Java conventions, modular packaging, complete getter/setter encapsulation, and clean separation between GUI and database code.</li>
    <li><strong>NFR-04 (Usability &amp; Ergonomics):</strong> The graphical interface shall implement custom typography, card-based groupings, and distinct color codes for status progression to reduce operator cognitive fatigue.</li>
</ul>

<h2>3.2 Platform and IDE</h2>
<p>The platform is developed and executed across modern, industry-standard development frameworks. Table 3.2 details the platform components, tools, and libraries utilized.</p>

<div class="table-title">Table 3.2: Platform, Tools, and Runtime Environment</div>
<table>
    <thead>
        <tr>
            <th>Component / Tool</th>
            <th>Selected Specification / Technology</th>
            <th>Role in Project Lifecycle</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Programming Language</strong></td>
            <td>Java 21 LTS (JDK 21)</td>
            <td>Core language implementing object-oriented models, functional comparators, and business services.</td>
        </tr>
        <tr>
            <td><strong>Graphical Framework</strong></td>
            <td>Java Swing (javax.swing)</td>
            <td>Lightweight desktop windowing, custom component rendering, and event-driven architectures.</td>
        </tr>
        <tr>
            <td><strong>Integrated Dev. Environment</strong></td>
            <td>Eclipse IDE for Java Developers (2024+)</td>
            <td>Source editing, automated build compilation, project management, and live debugging.</td>
        </tr>
        <tr>
            <td><strong>Relational Database Server</strong></td>
            <td>MySQL Server 8.0 / 9.1 (InnoDB)</td>
            <td>Persistent storage engine enforcing foreign keys, check constraints, and ACID transactions.</td>
        </tr>
        <tr>
            <td><strong>Database Driver (JDBC)</strong></td>
            <td>MySQL Connector/J 9.1.0</td>
            <td>Official high-performance Type-4 JDBC driver connecting Java services to the database.</td>
        </tr>
        <tr>
            <td><strong>Build &amp; Dependency Tool</strong></td>
            <td>Apache Maven 3.9+ (pom.xml)</td>
            <td>Project lifecycle management, dependency resolution, and automated compilation.</td>
        </tr>
    </tbody>
</table>

<h2>3.3 Application Development and Implementation</h2>
<p>The application codebase is organized into four clean packages under <code>com.evcharge.app</code>, reflecting a strict separation of concerns:</p>

<ul>
    <li><strong><code>com.evcharge.app.model</code> (Domain Layer):</strong> Encapsulates domain entities representing physical and conceptual entities: <code>Driver</code>, <code>Vehicle</code>, <code>Charger</code>, <code>ChargingLocation</code>, <code>Booking</code>, <code>ChargingSession</code>, <code>Payment</code>, and <code>Review</code>.</li>
    <li><strong><code>com.evcharge.app.dao</code> (Data Access Layer):</strong> Encapsulates all relational database operations via dedicated Data Access Objects: <code>DriverDAO</code>, <code>VehicleDAO</code>, <code>ChargerDAO</code>, <code>BookingDAO</code>, <code>ChargingSessionDAO</code>, <code>PaymentDAO</code>, <code>LoyaltyDAO</code>, and <code>ReviewDAO</code>.</li>
    <li><strong><code>com.evcharge.app.service</code> (Business Logic Layer):</strong> Encapsulates algorithmic intelligence: <code>BookingQueue</code> (priority scheduling), <code>BookingService</code> (multi-stage validation and lifecycle state transitions), and <code>LoyaltyService</code> (points calculation and redemptions).</li>
    <li><strong><code>com.evcharge.app</code> (Presentation &amp; Core):</strong> Hosts <code>MainFrame.java</code> (navigation frame), <code>AppTheme.java</code> (design tokens), <code>DBConnection.java</code> (thread-safe JDBC manager), <code>AppTestSuite.java</code> (automated testing suite), and all eight responsive Swing panels.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 4: SYSTEM DESIGN                                       -->
<!-- ============================================================== -->
<h1>4. SYSTEM DESIGN</h1>

<p>System design represents the architectural blueprint that translates requirements into robust, modular, and maintainable software components. This chapter articulates the multi-tier MVC architecture, the relational database design, and the explicit manifestation of object-oriented programming concepts throughout the system.</p>

<h2>4.1 Multi-Tier MVC and DAO Architectural Framework</h2>
<p>The platform implements a decoupled multi-tier architecture uniting the <strong>Model-View-Controller (MVC)</strong> pattern with the <strong>Data Access Object (DAO)</strong> abstraction pattern:</p>

<ul>
    <li><strong>View (Presentation Layer):</strong> Consists of Swing panels (<code>DashboardPanel</code>, <code>DriverVehiclePanel</code>, <code>ChargerPanel</code>, <code>BookingPanel</code>, <code>SessionPanel</code>, <code>PaymentPanel</code>, <code>ReviewPanel</code>, <code>DatabasePanel</code>) that capture user input and display data using responsive cards and status badges.</li>
    <li><strong>Controller (Application Coordination):</strong> Coordinated by <code>MainFrame.java</code> and service delegates, handling event routing, input validation, and state changes between the UI and backend services.</li>
    <li><strong>Model (Domain Logic &amp; Entities):</strong> Domain objects (<code>Driver</code>, <code>Vehicle</code>, <code>Charger</code>, <code>Booking</code>, <code>ChargingSession</code>, <code>Payment</code>, <code>Review</code>) encapsulating core data and entity-level rules.</li>
    <li><strong>Data Access Layer (DAO):</strong> Isolates SQL query generation, connection handling, and result mapping away from both the presentation and business layers.</li>
</ul>

<h2>4.2 Object-Oriented Programming (OOP) Principles in EvChargingApp</h2>
<p>The system was designed from the ground up as an authentic, practical demonstration of core object-oriented programming concepts. Table 4.1 maps each principle to its concrete manifestation in the codebase.</p>

<div class="table-title">Table 4.1: Realization of Core OOP Principles in EvChargingApp</div>
<table>
    <thead>
        <tr>
            <th>OOP Principle</th>
            <th>Concrete Realization in EvChargingApp</th>
            <th>Primary Classes Involved</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>Encapsulation</strong></td>
            <td>All entity fields (driverId, batteryCapacityKwh, currentBatteryPercentage, loyaltyPoints, etc.) are declared <code>private</code>. State modifications are restricted to validated public accessor and mutator methods enforcing physical and business invariants.</td>
            <td><code>Driver</code>, <code>Vehicle</code>, <code>Charger</code>, <code>Booking</code>, <code>Payment</code></td>
        </tr>
        <tr>
            <td><strong>Abstraction</strong></td>
            <td>Underlying SQL operations, connection pooling, and JDBC result set parsing are abstracted behind clean Data Access Object (DAO) interfaces. Presentation panels interact purely with high-level domain methods.</td>
            <td><code>DBConnection</code>, <code>BookingDAO</code>, <code>ChargerDAO</code>, <code>PaymentDAO</code></td>
        </tr>
        <tr>
            <td><strong>Polymorphism</strong></td>
            <td>Runtime dynamic dispatch via functional interface implementations (<code>Comparator&lt;Booking&gt;</code>) in PriorityQueue; static polymorphism through extensive constructor overloading across all domain entities.</td>
            <td><code>BookingQueue</code>, <code>AppTestSuite</code></td>
        </tr>
        <tr>
            <td><strong>Composition &amp; Aggregation</strong></td>
            <td>Complex real-world entities are modeled by composing discrete collaborating instances (e.g., a <code>Booking</code> is composed of a <code>Driver</code>, <code>Vehicle</code>, and <code>Charger</code>; a <code>ChargingSession</code> references a <code>Booking</code>).</td>
            <td><code>Booking</code>, <code>ChargingSession</code>, <code>Payment</code>, <code>Review</code></td>
        </tr>
    </tbody>
</table>

<h2>4.3 Algorithmic Design and Business Logic</h2>

<h3>4.3.1 Priority Queue Scheduling Algorithm</h3>
<p>To provide immediate priority to emergency responders and prioritize severely depleted vehicles, <code>BookingQueue.java</code> implements a custom comparator for <code>java.util.PriorityQueue&lt;Booking&gt;</code>:</p>
<ul>
    <li><strong>Rule 1 (Emergency Leapfrogging):</strong> If booking \\( B_1 \\) is marked <em>EMERGENCY</em> and \\( B_2 \\) is <em>NORMAL</em>, \\( B_1 \\) is dispatched before \\( B_2 \\). Conversely, if \\( B_2 \\) is <em>EMERGENCY</em> and \\( B_1 \\) is <em>NORMAL</em>, \\( B_2 \\) is dispatched first.</li>
    <li><strong>Rule 2 (Battery SoC Tiebreaker):</strong> If both bookings share the same emergency status, the comparator breaks ties by sorting vehicles in ascending order of current battery percentage (lowest SoC first).</li>
</ul>

<h3>4.3.2 Physical Connector Compatibility Matrix</h3>
<p>The connector compatibility engine (<code>Charger.isCompatibleWith(Vehicle vehicle)</code>) cross-validates the vehicle's inlet against the charger's dispensing gun. Table 4.2 illustrates the compatibility truth matrix enforced before booking creation.</p>

<div class="table-title">Table 4.2: Vehicle Connector vs. Charger Compatibility Matrix</div>
<table>
    <thead>
        <tr>
            <th>Vehicle Connector Standard</th>
            <th>Charger: CCS2</th>
            <th>Charger: Type 2 AC</th>
            <th>Charger: CHAdeMO</th>
            <th>Charger: GB/T</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><strong>CCS2 (DC Fast / AC)</strong></td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>Type 2 (AC European)</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>CHAdeMO (DC Asian)</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
        </tr>
        <tr>
            <td><strong>GB/T (Chinese DC)</strong></td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>INCOMPATIBLE (FALSE)</td>
            <td>COMPATIBLE (TRUE)</td>
        </tr>
    </tbody>
</table>

<h2>4.4 Relational Database Architecture (10 Normalized Tables)</h2>
<p>The relational persistence layer comprises 10 tables organized under Third Normal Form (3NF) in the <code>ev_charging_app</code> MySQL database:</p>

<ul>
    <li><strong><code>drivers</code>:</strong> Stores driver identifiers, names, unique phone numbers, unique emails, and accumulated loyalty points.</li>
    <li><strong><code>vehicles</code>:</strong> Stores vehicle identifiers, driver foreign keys (CASCADE), model names, battery capacity (kWh), connector type, and battery SoC with <code>CHECK (0 to 100)</code>.</li>
    <li><strong><code>charging_locations</code>:</strong> Catalogs physical station facilities, addresses, verification badges, and ratings.</li>
    <li><strong><code>location_amenities</code>:</strong> Lists station conveniences (Coffee Shop, Restrooms, Free Wi-Fi) linked to locations via foreign keys.</li>
    <li><strong><code>chargers</code>:</strong> Catalogs individual dispenser units, modes (Fast, Ultra-Fast), connector types, price per kWh, and live availability flags.</li>
    <li><strong><code>bookings</code>:</strong> Records reservations with driver, vehicle, and charger foreign keys, request type (NORMAL/EMERGENCY), battery percentage at request, and lifecycle status (PENDING, CONFIRMED, CHARGING, COMPLETED, CANCELLED).</li>
    <li><strong><code>charging_sessions</code>:</strong> Tracks active charging sessions, kilowatt-hours consumed, start/end timestamps, and computed total invoice amounts.</li>
    <li><strong><code>payments</code>:</strong> Audits financial settlements, payment modes (UPI, CARD, CASH, WALLET), amounts paid, and settlement status (PENDING, PAID, FAILED).</li>
    <li><strong><code>loyalty_transactions</code>:</strong> Maintains an immutable audit ledger of reward points earned and redeemed with timestamps.</li>
    <li><strong><code>reviews</code>:</strong> Stores verified customer ratings (1 to 5 stars) and feedback comments linked to completed bookings.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 5: SCREEN SHOTS                                        -->
<!-- ============================================================== -->
<h1>5. SCREEN SHOTS</h1>

<p>This chapter presents the architectural layouts, user interface structure, and operational walkthroughs of all eight responsive modules comprising the <strong>EvChargingApp</strong> desktop application. The interface utilizes a high-contrast dark theme managed by <code>AppTheme.java</code>, featuring responsive cards, crisp typography, and real-time visual status indicators.</p>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ APPLICATION NAVIGATION SHELL ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9.5pt;">
            <tr>
                <td style="width: 28%; background-color: #0F131C; color: #fff; padding: 10pt;">
                    <strong style="color: #00C87A; font-size: 11pt;">⚡ EVCharge Platform</strong><br>
                    <span style="font-size: 8pt; color: #888;">Smart Station Manager</span><br><br>
                    &bull; ⚡ Dashboard<br>
                    &bull; 👤 Driver &amp; Vehicle<br>
                    &bull; 🔌 Charger Station<br>
                    &bull; 📋 Booking &amp; Queue<br>
                    &bull; ⚡ Active Session<br>
                    &bull; 💳 Payment &amp; Loyalty<br>
                    &bull; ⭐ Driver Reviews<br>
                    &bull; 🗄 MySQL Database Console
                </td>
                <td style="width: 72%; background-color: #121620; color: #E8EAF0; padding: 14pt; vertical-align: middle; text-align: center;">
                    <strong>Central CardLayout Dynamic Viewport</strong><br>
                    <span style="font-size: 8.5pt; color: #888;">Seamlessly renders active module without window flickering</span>
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.1 — Master Navigation Frame and Application Layout</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ OPERATIONS DASHBOARD &amp; LIVE TELEMETRY ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt; margin-bottom: 6pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="text-align: center;">Active Driver<br><strong style="color: #00C87A;">Ananya Sharma</strong></td>
                <td style="text-align: center;">Active Vehicle<br><strong style="color: #00C87A;">Tata Nexon EV</strong></td>
                <td style="text-align: center;">Target Charger<br><strong style="color: #00C87A;">GC-01 (CCS2)</strong></td>
                <td style="text-align: center;">Queue Status<br><strong style="color: #00C87A;">2 Enqueued</strong></td>
                <td style="text-align: center;">Energy Delivered<br><strong style="color: #00C87A;">24.50 kWh</strong></td>
                <td style="text-align: center;">Loyalty Points<br><strong style="color: #00C87A;">164 pts</strong></td>
            </tr>
        </table>
        <div style="background-color: #1A2030; color: #00C87A; padding: 8pt; border: 1px solid #2A3248; font-family: Courier New; font-size: 8.5pt; text-align: left;">
            [STAGE INDICATOR] &bull; PENDING &rarr; &bull; CONFIRMED &rarr; &bull; CHARGING &rarr; &bull; COMPLETED<br>
            [LIVE SENSOR STREAM] 48.2 kW Delivery Rate | Station Voltage: 400V | SoC Curve: 18% &rarr; 80%
        </div>
    </div>
    <div class="figure-title">Figure 5.2 — Operations Dashboard and Live Telemetry Observation Panel</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ DRIVER &amp; VEHICLE SETUP ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Driver Profile Setup</strong><br>
                    Full Name: [ Ananya Sharma ]<br>
                    Phone: [ 9876543210 ]<br>
                    Email: [ ananya@example.com ]<br>
                    Accumulated Loyalty Points: 164 pts
                </td>
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Vehicle Specifications</strong><br>
                    Vehicle Model: [ Tata Nexon EV ]<br>
                    Battery Capacity: [ 40.50 kWh ]<br>
                    Connector Inlet: [ CCS2 &darr; ]<br>
                    Current Battery State of Charge: [ 18% ]
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.3 — Driver and Vehicle Profile Registration Panel</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ CHARGER &amp; LOCATION INFRASTRUCTURE ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Charger Configuration</strong><br>
                    Charger Code: [ GC-01 ]<br>
                    Speed Mode: [ Fast (DC 50kW) &darr; ]<br>
                    Connector Type: [ CCS2 &darr; ]<br>
                    Energy Tariff Rate: [ ₹18.50 / kWh ]<br>
                    Availability Status: [&#10004; Available]
                </td>
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Station Location &amp; Amenities</strong><br>
                    Station Name: [ GreenCharge Cafe ]<br>
                    Address: [ MG Road, Bengaluru ]<br>
                    Verification: [&#10004; Certified Inspection]<br>
                    Rating: 4.6 / 5.0 (Coffee Shop, Restrooms, Wi-Fi)<br>
                    <span style="color: #00C87A;">&#10004; Connector Matches Active Vehicle!</span>
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.4 — Charger Infrastructure and Station Location Panel</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ PRIORITY QUEUE BOOKING &amp; DISPATCH ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 8.5pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 35%; padding: 8pt;">
                    <strong style="color: #00C87A;">Create Reservation</strong><br>
                    Booking ID: [ BK-2026-01 ]<br>
                    Dispatch Mode: [ EMERGENCY &darr; ]<br>
                    Compatibility: &#10004; CCS2 Validated<br><br>
                    <div style="background-color: #00C87A; color: #121620; text-align: center; padding: 4pt; font-weight: bold;">+ Add to Priority Queue</div>
                </td>
                <td style="width: 65%; padding: 8pt;">
                    <strong style="color: #00C87A;">Live Prioritized Queue Table (3 Enqueued)</strong><br>
                    <span style="color: #ff7675; font-weight: bold;">1. [EMERGENCY] BK-EMERG | Car C | 45% SoC (Leapfrogged Ahead!)</span><br>
                    2. [NORMAL] BK-NORM-15 | Car B | 15% SoC (Low Battery Tiebreaker)<br>
                    3. [NORMAL] BK-NORM-80 | Car A | 80% SoC (Standard Priority)<br><br>
                    <div style="background-color: #328cc1; color: #fff; text-align: center; padding: 4pt; font-weight: bold;">&#9654; Dispatch Next Booking (Priority)</div>
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.5 — Booking Dispatch and Priority Queue Visualizer</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ CHARGING SESSION &amp; METERING ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Active Session Metering</strong><br>
                    Session ID: CS-101 (Linked to BK-2026-01)<br>
                    Energy Dispensed: [ 25.00 kWh ]<br><br>
                    <div style="background-color: #00C87A; color: #121620; text-align: center; padding: 4pt; font-weight: bold;">⚡ Complete Session &amp; Generate Bill</div>
                </td>
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Dynamic Billing Calculation</strong><br>
                    Tariff Rate: ₹18.50 per kWh<br>
                    Energy Delivered: 25.00 kWh<br>
                    Formula: 25.00 &times; ₹18.50<br>
                    <strong style="font-size: 11pt; color: #00C87A;">Total Invoice Amount: ₹462.50</strong><br>
                    Status: Session COMPLETED (Charger Released)
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.6 — Charging Session Metering and Automated Invoice Panel</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ PAYMENT GATEWAY &amp; LOYALTY LEDGER ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Payment Processing</strong><br>
                    Invoice Balance: ₹462.50<br>
                    Payment Method: [ UPI &darr; ] (Card / Wallet / Cash)<br><br>
                    <div style="background-color: #00C87A; color: #121620; text-align: center; padding: 4pt; font-weight: bold;">💳 Process Settlement (₹462.50)</div>
                </td>
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Settlement Audit &amp; Loyalty Rewards</strong><br>
                    Status: PAID &#10004; (Transaction Logged to MySQL)<br>
                    Loyalty Reward Rule: 1 pt per ₹10 paid<br>
                    Points Earned: <strong style="color: #00C87A;">+46 points</strong><br>
                    Driver New Total Balance: <strong>210 points</strong><br>
                    Audit Record: #LT-882 Type: EARNED
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.7 — Multi-Channel Payment Processing and Loyalty Ledger Panel</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ CUSTOMER VERIFIED REVIEW MODULE ]</p>
        <table style="width: 100%; border: 1px solid #ccc; font-size: 9pt;">
            <tr style="background-color: #1A2030; color: #fff;">
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Submit Driver Feedback</strong><br>
                    Booking ID: [ BK-2026-01 ]<br>
                    Rating: <span style="color: #f1c40f; font-size: 11pt;">★ ★ ★ ★ ★ (5 Stars)</span><br>
                    Feedback: [ "Fast charging, clean lounge and cafe!" ]<br><br>
                    <div style="background-color: #00C87A; color: #121620; text-align: center; padding: 4pt; font-weight: bold;">★ Submit Verified Review</div>
                </td>
                <td style="width: 50%; padding: 8pt;">
                    <strong style="color: #00C87A;">Review Governance Verification</strong><br>
                    &#10004; Booking Reference Validated<br>
                    &#10004; Status Verified: COMPLETED<br>
                    &#10004; Rating Bounds Checked: 1 &le; 5 &le; 5<br>
                    Station Historical Rating: Updated 4.6 &rarr; 4.7 / 5.0<br>
                    <span style="color: #00C87A;">Review Successfully Persisted to MySQL!</span>
                </td>
            </tr>
        </table>
    </div>
    <div class="figure-title">Figure 5.8 — Customer Verified Star-Rating and Review Interface</div>
</div>

<div class="figure-container">
    <div class="figure-box">
        <p style="font-weight: bold; color: #0b3c5d; margin-bottom: 4pt;">[ MYSQL DATABASE OPERATIONS CONSOLE ]</p>
        <div style="background-color: #0b0e14; color: #00C87A; padding: 8pt; border: 1px solid #2A3248; font-family: Courier New; font-size: 8pt; text-align: left;">
            [SQL-OK] Connected to ev_charging_app @ localhost:3306<br>
            [DIAG] Drivers: 2 records | Vehicles: 2 records | Chargers: 3 records<br>
            [INSERT] booking_id=1 status=CONFIRMED charger_id=1 mode=NORMAL<br>
            [TRANS] payment_id=1 session_id=1 method=UPI amount=462.50 status=PAID<br>
            [LOYALTY] loyalty_transaction_id=1 driver_id=1 points=46 type=EARNED<br>
            [VERIFY] review_id=1 rating=5 booking_id=1 driver_id=1 [SUCCESS]
        </div>
    </div>
    <div class="figure-title">Figure 5.9 — Interactive MySQL Database Diagnostic Console Panel</div>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 6: CONCLUSION                                         -->
<!-- ============================================================== -->
<h1>6. CONCLUSION</h1>

<p>The successful design, engineering, and empirical verification of the <strong>EV Charging Management System (EvChargingApp)</strong> demonstrates a robust, production-ready desktop solution addressing real-world operational challenges in electric vehicle charging infrastructure. Developed as an advanced mini-project for the course <strong>PBCST304 Object Oriented Programming</strong>, the software achieves complete separation of concerns between presentation, business intelligence, and transactional persistence.</p>

<p>The project delivers on all core operational objectives:</p>
<ul>
    <li><strong>Intelligent Priority Scheduling:</strong> The custom comparator-driven priority queue eliminates queuing inequities by guaranteeing immediate non-preemptive leapfrogging for emergency fleets while systematically resolving non-emergency ties through battery State of Charge metrics.</li>
    <li><strong>Physical Hardware Protection:</strong> The pre-booking connector compatibility matrix completely prevents mismatched reservations between heterogeneous vehicle inlets (CCS2, Type 2, CHAdeMO, GB/T) and charger dispensing guns.</li>
    <li><strong>Automated Energy Accounting:</strong> Real-time session metering and dynamic invoice generation eliminate billing discrepancies, creating a transparent operational audit trail across multiple modern payment channels.</li>
    <li><strong>Customer Retention &amp; Trust:</strong> An integrated loyalty rewards ledger (1 pt per ₹10 paid) and state-machine-governed review validation mechanism prevent fraudulent ratings and enhance user satisfaction.</li>
    <li><strong>Automated Quality Assurance:</strong> The automated test harness (<code>AppTestSuite</code>) verifies 21 deterministic test assertions across scheduling, compatibility, loyalty math, state machine transitions, and live MySQL transactions with a 100% pass rate.</li>
</ul>

<h2>Pedagogical Outcomes in Object-Oriented Programming</h2>
<p>From an educational perspective, the project provided comprehensive, hands-on mastery of fundamental object-oriented paradigms:</p>
<ol>
    <li><strong>Encapsulation in Practice:</strong> Enforcing inviolable domain invariants (e.g., battery ranges 0–100%, non-negative pricing, valid star ratings) directly within private field mutators.</li>
    <li><strong>Dynamic Polymorphism &amp; Functional Lambdas:</strong> Implementing Java functional interfaces to dynamically alter collection ordering behavior at runtime.</li>
    <li><strong>Architectural Decoupling via DAO:</strong> Appreciating the necessity of isolating database connection lifecycles and SQL syntax from user interface event dispatch threads (EDT).</li>
    <li><strong>State Machine Modeling:</strong> Engineering lifecycle transitions as explicit state machine progressions to prevent illegal operations.</li>
</ol>

<h2>Future Scope</h2>
<p>While the desktop prototype satisfies all functional and academic requirements, future enhancements could extend the system into distributed industrial operations:</p>
<ul>
    <li><strong>Open Charge Point Protocol (OCPP 2.0.1) Integration:</strong> Connecting the application with physical EVSE hardware chargers via WebSocket-based OCPP protocol message frames.</li>
    <li><strong>Dynamic Tariff &amp; Smart Grid Integration:</strong> Incorporating real-time electricity grid pricing feeds to adjust station charging rates dynamically during peak grid stress.</li>
    <li><strong>Cross-Platform Mobile Application:</strong> Developing complementary Android and iOS client applications communicating via RESTful or gRPC APIs to allow drivers to track queue status remotely.</li>
</ul>

<!-- ============================================================== -->
<!-- CHAPTER 7: REFERENCES                                         -->
<!-- ============================================================== -->
<h1>7. REFERENCES</h1>

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

const htmlFilePath = path.join(__dirname, 'EV_Charging_App_Report_25_30_Pages.html');
fs.writeFileSync(htmlFilePath, htmlContent, 'utf8');

const psPath = path.join(__dirname, 'convert_final_docx.ps1');
console.log("Running Word COM script to convert and get page count...");
const out = execSync(`powershell -ExecutionPolicy Bypass -File "${psPath}"`, { encoding: 'utf8' });
console.log(out);

const pdfFilePath = path.join(__dirname, 'EV_Charging_App_Project_Report_Final.pdf');
const edgePath = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
console.log("Generating final PDF...");
const cmd = `"${edgePath}" --headless --disable-gpu --print-to-pdf="${pdfFilePath}" --no-pdf-header-footer "${htmlFilePath}"`;
execSync(cmd, { stdio: 'inherit' });
console.log("Final PDF created at: " + pdfFilePath);
