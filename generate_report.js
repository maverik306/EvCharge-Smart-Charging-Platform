const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

console.log("Building comprehensive 25+ page Project Report for EvChargingApp...");

const htmlContent = `<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>EV Charging App - Project Report (PBCST304)</title>
<style>
@page {
    size: A4 portrait;
    margin: 25mm 20mm 25mm 25mm;
    @top-right {
        content: "PBCST304: Object Oriented Programming";
        font-family: "Times New Roman", Times, serif;
        font-size: 9pt;
        color: #555555;
    }
    @bottom-center {
        content: counter(page);
        font-family: "Times New Roman", Times, serif;
        font-size: 10pt;
    }
}

@page :first {
    @top-right { content: normal; }
    @bottom-center { content: normal; }
}

* {
    box-sizing: border-box;
}

body {
    font-family: "Times New Roman", Times, serif;
    font-size: 12pt;
    line-height: 1.5;
    color: #111111;
    margin: 0;
    padding: 0;
    background-color: #ffffff;
}

.page-break {
    page-break-before: always;
    break-before: page;
}

.avoid-break {
    page-break-inside: avoid;
    break-inside: avoid;
}

/* Chapter/Section title: Times New Roman, 14, Bold, Center */
h1.chapter-title {
    font-family: "Times New Roman", Times, serif;
    font-size: 14pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    margin-top: 15pt;
    margin-bottom: 22pt;
    letter-spacing: 0.5px;
    page-break-before: always;
}

h1.chapter-title-first {
    font-family: "Times New Roman", Times, serif;
    font-size: 14pt;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    margin-top: 5pt;
    margin-bottom: 22pt;
    letter-spacing: 0.5px;
}

/* Heading 2: Times New Roman, 12, Bold, Left, 3 lines space before */
h2.section-heading {
    font-family: "Times New Roman", Times, serif;
    font-size: 12pt;
    font-weight: bold;
    text-align: left;
    margin-top: 36pt; /* 3 lines spacing */
    margin-bottom: 10pt;
}

/* Heading 3: Times New Roman, 12, Bold, Left */
h3.sub-section-heading {
    font-family: "Times New Roman", Times, serif;
    font-size: 12pt;
    font-weight: bold;
    text-align: left;
    margin-top: 18pt;
    margin-bottom: 8pt;
}

/* Body: Times New Roman, 12, Normal, 1.5 line spacing, Justified */
p {
    font-family: "Times New Roman", Times, serif;
    font-size: 12pt;
    font-weight: normal;
    line-height: 1.5;
    text-align: justify;
    text-justify: inter-word;
    margin-top: 0;
    margin-bottom: 12pt;
}

ul, ol {
    margin-top: 0;
    margin-bottom: 12pt;
    padding-left: 24pt;
}

li {
    font-family: "Times New Roman", Times, serif;
    font-size: 12pt;
    line-height: 1.5;
    text-align: justify;
    margin-bottom: 6pt;
}

/* Table: Title ABOVE table */
.table-wrapper {
    margin-top: 16pt;
    margin-bottom: 22pt;
    page-break-inside: avoid;
}

.table-title {
    font-family: "Times New Roman", Times, serif;
    font-size: 11pt;
    font-weight: bold;
    text-align: left;
    margin-bottom: 6pt;
}

table {
    width: 100%;
    border-collapse: collapse;
    font-family: "Times New Roman", Times, serif;
    font-size: 10pt;
    line-height: 1.4;
    margin-bottom: 4pt;
}

table, th, td {
    border: 1px solid #333333;
}

th {
    background-color: #f2f2f2;
    font-weight: bold;
    text-align: left;
    padding: 5pt 7pt;
}

td {
    padding: 5pt 7pt;
    vertical-align: top;
}

/* Figure: Title UNDER figure */
.figure-wrapper {
    margin-top: 18pt;
    margin-bottom: 22pt;
    text-align: center;
    page-break-inside: avoid;
}

.figure-box {
    display: inline-block;
    width: 98%;
    border: 1px solid #bbbbbb;
    padding: 10pt;
    background-color: #fafbfc;
    border-radius: 4px;
    text-align: center;
}

.figure-title {
    font-family: "Times New Roman", Times, serif;
    font-size: 11pt;
    font-weight: bold;
    text-align: center;
    margin-top: 8pt;
    margin-bottom: 4pt;
}

.code-block {
    font-family: "Courier New", Courier, monospace;
    font-size: 9pt;
    line-height: 1.35;
    background-color: #f6f8fa;
    border: 1px solid #d0d7de;
    padding: 8pt 10pt;
    margin: 10pt 0 12pt 0;
    white-space: pre-wrap;
    border-left: 3px solid #005a9e;
    page-break-inside: avoid;
    text-align: left;
}

.diagram-svg {
    max-width: 100%;
    height: auto;
    margin: 0 auto;
    display: block;
}

/* Cover Page */
.cover-container {
    text-align: center;
    padding-top: 30pt;
}
.cover-header {
    font-size: 13pt;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 1px;
    margin-bottom: 25pt;
    color: #222222;
}
.cover-project-title {
    font-size: 20pt;
    font-weight: bold;
    line-height: 1.3;
    margin-bottom: 12pt;
    text-transform: uppercase;
    color: #0b3c5d;
}
.cover-subtitle {
    font-size: 12pt;
    font-weight: bold;
    color: #444444;
    line-height: 1.5;
    margin-bottom: 35pt;
}
.cover-course-box {
    border-top: 2px solid #0b3c5d;
    border-bottom: 2px solid #0b3c5d;
    padding: 14pt 0;
    margin: 25pt 0;
}
.cover-course-title {
    font-size: 13pt;
    font-weight: bold;
    color: #0b3c5d;
}
.cover-meta {
    font-size: 11.5pt;
    line-height: 1.8;
    margin-top: 40pt;
    margin-bottom: 40pt;
}
.cover-date {
    font-size: 12pt;
    font-weight: bold;
    margin-top: 30pt;
}
</style>
</head>
<body>

<!-- ========================================== -->
<!-- PAGE 1: TITLE / COVER PAGE                 -->
<!-- ========================================== -->
<div class="cover-container">
    <div class="cover-header">Department of Computer Science &amp; Engineering</div>
    
    <div style="margin-top: 30pt;">
        <div class="cover-project-title">EV CHARGING MANAGEMENT SYSTEM<br>(EvChargingApp)</div>
        <div class="cover-subtitle">A Multi-Tier Object-Oriented Desktop Platform for Intelligent Priority Dispatching, Real-Time Telemetry Observation, Metered Billing, and Loyalty Incentives</div>
    </div>

    <div class="cover-course-box">
        <div class="cover-course-title">COURSE CODE: PBCST304</div>
        <div style="font-size: 12pt; font-weight: bold; margin-top: 4pt;">COURSE: OBJECT ORIENTED PROGRAMMING</div>
    </div>

    <div style="margin: 35pt 0;">
        <p style="text-align: center; font-style: italic;">A Project Report submitted in partial fulfillment of the requirements for the award of the Degree of Bachelor of Technology</p>
    </div>

    <div class="cover-meta">
        <table style="width: 80%; margin: 0 auto; border: none;">
            <tr style="border: none;">
                <td style="border: none; width: 50%; text-align: left; vertical-align: top;">
                    <strong>Submitted By:</strong><br>
                    Student Name: Project Team<br>
                    Registration No: 2024-PBCST-304<br>
                    Semester: V / Academic Year: 2026
                </td>
                <td style="border: none; width: 50%; text-align: right; vertical-align: top;">
                    <strong>Under the Guidance of:</strong><br>
                    Course Instructor / Faculty Evaluator<br>
                    Department of Computer Science<br>
                    School of Engineering &amp; Technology
                </td>
            </tr>
        </table>
    </div>

    <div class="cover-date">
        <strong>Date of Submission: 27.10.2026</strong>
    </div>
</div>

<!-- ========================================== -->
<!-- PAGE 2: CERTIFICATE OF EVALUATION          -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title-first">CERTIFICATE OF EVALUATION</h1>

<p>This is to certify that the project report entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> is a bona fide record of independent technical work carried out for the course <strong>PBCST304 Object Oriented Programming</strong> by the undersigned student(s) in partial fulfillment of the academic curriculum requirements.</p>

<p>The system embodies the practical application of object-oriented principles including encapsulation, inheritance, polymorphism, abstraction, and multi-tier architectural decoupling using Java 21, the Java Swing GUI framework, custom design token architectures, and persistent relational data storage via MySQL and MySQL Connector/J 9.1.0.</p>

<p>The softcopy of this report has been verified and approved for final evaluation and documentation on the stipulated deadline of <strong>27.10.2026</strong>.</p>

<div style="margin-top: 70pt;">
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

<div style="margin-top: 60pt;">
    <div class="table-wrapper">
        <div class="table-title">Table 0.1: Project Evaluation &amp; Grade Rubric Sheet</div>
        <table>
            <thead>
                <tr>
                    <th>Evaluation Criteria</th>
                    <th>Max Marks</th>
                    <th>Marks Awarded</th>
                    <th>Evaluator Remarks</th>
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
    </div>
</div>

<!-- ========================================== -->
<!-- PAGE 3: ACKNOWLEDGEMENT & DECLARATION      -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title-first">ACKNOWLEDGEMENT</h1>

<p>The successful conceptualization, structural design, implementation, and rigorous verification of the <strong>EV Charging Management System (EvChargingApp)</strong> has been an enriching academic and practical endeavor. We express our profound gratitude to our course instructor and laboratory mentors for the course <strong>PBCST304 Object Oriented Programming</strong> for their continuous intellectual guidance, constructive feedback, and architectural insights throughout the lifecycle of this project.</p>

<p>We extend our sincere thanks to the Department of Computer Science &amp; Engineering for providing access to robust computing environments, developmental workstations, and testing sandboxes equipped with Java Development Kit 21, the Eclipse IDE ecosystem, and enterprise MySQL relational database servers.</p>

<p>Finally, we express our heartfelt appreciation to our peers, families, and academic colleagues whose collaborative discussions, encouragement, and technical suggestions helped refine our priority dispatching algorithms, multi-method payment workflows, and unit test suites.</p>

<h2 class="section-heading" style="margin-top: 40pt;">DECLARATION</h2>

<p>We hereby declare that this project report entitled <strong>"EV Charging Management System (EvChargingApp)"</strong> represents our original intellectual work undertaken for <strong>PBCST304 Object Oriented Programming</strong>. The algorithms, architectural designs, class models, database schemas, and implementation codes presented herein have been engineered by our team, and all external literature, specifications, third-party libraries (such as MySQL Connector/J), and references have been duly recognized and cited.</p>

<div style="margin-top: 50pt;">
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

<!-- ========================================== -->
<!-- PAGE 4: ABSTRACT                           -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title-first">ABSTRACT</h1>

<p>With the exponential global transition toward sustainable electromobility, electric vehicle (EV) charging station infrastructure faces severe operational challenges, including physical connector mismatching, unpredictable queuing bottlenecks, ad-hoc billing discrepancies, and fragmented user feedback mechanisms. This project presents the design and implementation of the <strong>EV Charging Management System (EvChargingApp)</strong>, a comprehensive desktop-based software solution engineered in <strong>Java 21</strong> adhering to rigorous <strong>Object-Oriented Programming (OOP)</strong> paradigms.</p>

<p>The platform is architected around a multi-tier Model-View-Controller (MVC) and Data Access Object (DAO) pattern, guaranteeing separation of concerns between graphical presentation, domain logic, and transactional persistence. Key system innovations include: (1) a <strong>Dynamic Priority Queue Engine</strong> implemented via a custom comparator that intelligently schedules vehicle charging requests by granting non-preemptive leapfrogging priority to <em>EMERGENCY</em> vehicles while resolving non-emergency ties through battery State of Charge (SoC); (2) a strict <strong>Physical Connector Compatibility Validation Layer</strong> preventing incompatible vehicle-to-charger bookings (e.g., CCS2, Type 2, CHAdeMO, GB/T); (3) a dynamic <strong>Charging Session &amp; Automated Billing Engine</strong> calculating consumption bills based on exact kilowatt-hours (kWh) and station tariff rates; (4) a <strong>Multi-Channel Payment and Audit Ledger</strong> accommodating UPI, Credit/Debit Card, Digital Wallet, and Cash payment methods; (5) a mathematically guarded <strong>Customer Loyalty Rewards Program</strong> providing an earn rate of 1 reward point per ₹10 paid alongside transparent redemption mechanisms; and (6) a <strong>Verified Post-Session Review State Machine</strong> restricting customer ratings (1 to 5 stars) strictly to completed charging bookings.</p>

<p>The graphical user interface (GUI) is developed in <strong>Java Swing</strong> utilizing custom visual design tokens (<code>AppTheme</code>) featuring high-contrast typography, interactive card components, a real-time <strong>Telemetry Observation Stream</strong> simulating station voltage and kilowatt delivery, and an integrated <strong>Database Management Console</strong> for live diagnostic inspection. Data persistence is managed via an enterprise relational database schema comprising 10 normalized MySQL tables interconnected through foreign key constraints, unique indexes, and domain-level check constraints. The entire business logic and transactional pipeline are verified via an automated test suite (<code>AppTestSuite</code>) comprising 21 deterministic assertions covering scheduling, compatibility, loyalty mathematics, lifecycle transitions, review validations, and live database connectivity. This project stands as an exhaustive, industrially aligned demonstration of object-oriented modeling, data abstraction, encapsulation, and architectural robustness.</p>

<!-- ========================================== -->
<!-- PAGE 5: TABLE OF CONTENTS                  -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title-first">TABLE OF CONTENTS</h1>

<div class="table-wrapper">
    <table style="width: 100%; border: none;">
        <thead>
            <tr style="border-bottom: 2px solid #000;">
                <th style="border: none; width: 85%;">Section / Chapter Description</th>
                <th style="border: none; width: 15%; text-align: right;">Page No.</th>
            </tr>
        </thead>
        <tbody>
            <tr style="border: none;"><td style="border: none;">Certificate of Evaluation</td><td style="border: none; text-align: right;">ii</td></tr>
            <tr style="border: none;"><td style="border: none;">Acknowledgement &amp; Declaration</td><td style="border: none; text-align: right;">iii</td></tr>
            <tr style="border: none;"><td style="border: none;">Abstract</td><td style="border: none; text-align: right;">iv</td></tr>
            <tr style="border: none;"><td style="border: none;">Table of Contents</td><td style="border: none; text-align: right;">v</td></tr>
            <tr style="border: none;"><td style="border: none;">List of Figures</td><td style="border: none; text-align: right;">vi</td></tr>
            <tr style="border: none;"><td style="border: none;">List of Tables</td><td style="border: none; text-align: right;">vii</td></tr>
            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 1 — Introduction</td><td style="border: none; text-align: right;">1</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.1 Background and Domain Context</td><td style="border: none; text-align: right;">1</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.2 Motivation and Industry Challenges</td><td style="border: none; text-align: right;">1</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.3 Problem Statement</td><td style="border: none; text-align: right;">2</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.4 Project Objectives</td><td style="border: none; text-align: right;">2</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.5 Scope and Operational Boundaries</td><td style="border: none; text-align: right;">3</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">1.6 Organization of the Report</td><td style="border: none; text-align: right;">3</td></tr>
            
            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 2 — Literature Review &amp; System Comparison</td><td style="border: none; text-align: right;">4</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">2.1 Survey of Existing EV Infrastructure Platforms</td><td style="border: none; text-align: right;">4</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">2.2 Limitations of Conventional Management Approaches</td><td style="border: none; text-align: right;">4</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">2.3 Proposed Solution Architecture &amp; Distinct Advantages</td><td style="border: none; text-align: right;">5</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">2.4 Software Development Methodology</td><td style="border: none; text-align: right;">6</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 3 — System Requirements &amp; Database Architecture</td><td style="border: none; text-align: right;">7</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">3.1 Functional Requirements Specification</td><td style="border: none; text-align: right;">7</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">3.2 Non-Functional Requirements Specification</td><td style="border: none; text-align: right;">8</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">3.3 Hardware Requirements &amp; Sizing</td><td style="border: none; text-align: right;">8</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">3.4 Software Environment &amp; Technology Stack</td><td style="border: none; text-align: right;">9</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">3.5 Relational Database Schema &amp; Data Dictionary</td><td style="border: none; text-align: right;">10</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 4 — System Design &amp; Object-Oriented Programming Concepts</td><td style="border: none; text-align: right;">13</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">4.1 Multi-Tier MVC &amp; DAO Architectural Design</td><td style="border: none; text-align: right;">13</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">4.2 Class Hierarchy &amp; Domain Modeling</td><td style="border: none; text-align: right;">14</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">4.3 Demonstration of Core Object-Oriented Principles</td><td style="border: none; text-align: right;">15</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">4.4 Business Logic Algorithms &amp; Mathematical Formulations</td><td style="border: none; text-align: right;">18</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 5 — User Interface Design &amp; Functional Walkthrough</td><td style="border: none; text-align: right;">20</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">5.1 GUI Theming &amp; Design Token Architecture</td><td style="border: none; text-align: right;">20</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">5.2 Module-by-Module Walkthrough &amp; Screen Layouts</td><td style="border: none; text-align: right;">21</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 6 — Verification, Testing &amp; Quality Assurance</td><td style="border: none; text-align: right;">24</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">6.1 Automated Testing Strategy &amp; Harness</td><td style="border: none; text-align: right;">24</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">6.2 Test Matrix &amp; Execution Results (21 Test Assertions)</td><td style="border: none; text-align: right;">25</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 7 — Conclusion &amp; Future Scope</td><td style="border: none; text-align: right;">27</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">7.1 Summary of Deliverables &amp; Achievements</td><td style="border: none; text-align: right;">27</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">7.2 Pedagogical Outcomes in OOP</td><td style="border: none; text-align: right;">27</td></tr>
            <tr style="border: none;"><td style="border: none; padding-left: 15pt;">7.3 Limitations &amp; Future Enhancements</td><td style="border: none; text-align: right;">28</td></tr>

            <tr style="border-top: 1px solid #ccc; font-weight: bold;"><td style="border: none;">Chapter 8 — References &amp; Bibliography</td><td style="border: none; text-align: right;">29</td></tr>
        </tbody>
    </table>
</div>

<!-- ========================================== -->
<!-- PAGE 6: LIST OF FIGURES & LIST OF TABLES   -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title-first">LIST OF FIGURES</h1>

<div class="table-wrapper">
    <table style="width: 100%; border: none;">
        <thead>
            <tr style="border-bottom: 2px solid #000;">
                <th style="border: none; width: 20%;">Figure No.</th>
                <th style="border: none; width: 68%;">Figure Description</th>
                <th style="border: none; width: 12%; text-align: right;">Page No.</th>
            </tr>
        </thead>
        <tbody>
            <tr style="border: none;"><td>Figure 2.1</td><td>Iterative Object-Oriented Development Life Cycle</td><td style="text-align: right;">6</td></tr>
            <tr style="border: none;"><td>Figure 3.1</td><td>Entity Relationship (ER) Diagram of ev_charging_app Database</td><td style="text-align: right;">12</td></tr>
            <tr style="border: none;"><td>Figure 4.1</td><td>Multi-Tier MVC and DAO Software Architecture</td><td style="text-align: right;">13</td></tr>
            <tr style="border: none;"><td>Figure 4.2</td><td>UML Class Diagram of EvChargingApp Domain Entities</td><td style="text-align: right;">15</td></tr>
            <tr style="border: none;"><td>Figure 4.3</td><td>Booking and Session State Machine Flowchart</td><td style="text-align: right;">18</td></tr>
            <tr style="border: none;"><td>Figure 4.4</td><td>Priority Queue Comparator Scheduling Logic Flow</td><td style="text-align: right;">19</td></tr>
            <tr style="border: none;"><td>Figure 5.1</td><td>Main Navigation Frame and Application Layout</td><td style="text-align: right;">20</td></tr>
            <tr style="border: none;"><td>Figure 5.2</td><td>Operations Dashboard with Live Telemetry Observation Panel</td><td style="text-align: right;">21</td></tr>
            <tr style="border: none;"><td>Figure 5.3</td><td>Driver and Vehicle Registration &amp; Battery Setup Panel</td><td style="text-align: right;">22</td></tr>
            <tr style="border: none;"><td>Figure 5.4</td><td>Charger Infrastructure and Location Amenities Panel</td><td style="text-align: right;">22</td></tr>
            <tr style="border: none;"><td>Figure 5.5</td><td>Priority Queue Booking Dispatch Interface</td><td style="text-align: right;">23</td></tr>
            <tr style="border: none;"><td>Figure 5.6</td><td>Charging Session Metering &amp; Energy Delivery Panel</td><td style="text-align: right;">23</td></tr>
            <tr style="border: none;"><td>Figure 5.7</td><td>Multi-Channel Payment Processing &amp; Loyalty Ledger Panel</td><td style="text-align: right;">24</td></tr>
            <tr style="border: none;"><td>Figure 5.8</td><td>Customer Verified Star Rating and Review Interface</td><td style="text-align: right;">24</td></tr>
            <tr style="border: none;"><td>Figure 5.9</td><td>Interactive MySQL Database Diagnostic Console Panel</td><td style="text-align: right;">25</td></tr>
            <tr style="border: none;"><td>Figure 6.1</td><td>Automated Test Suite (AppTestSuite) Execution Flow</td><td style="text-align: right;">26</td></tr>
        </tbody>
    </table>
</div>

<h1 class="chapter-title-first" style="margin-top: 30pt;">LIST OF TABLES</h1>

<div class="table-wrapper">
    <table style="width: 100%; border: none;">
        <thead>
            <tr style="border-bottom: 2px solid #000;">
                <th style="border: none; width: 20%;">Table No.</th>
                <th style="border: none; width: 68%;">Table Description</th>
                <th style="border: none; width: 12%; text-align: right;">Page No.</th>
            </tr>
        </thead>
        <tbody>
            <tr style="border: none;"><td>Table 0.1</td><td>Project Evaluation &amp; Grade Rubric Sheet</td><td style="text-align: right;">ii</td></tr>
            <tr style="border: none;"><td>Table 2.1</td><td>Comparative Matrix: Traditional vs. EvChargingApp Approach</td><td style="text-align: right;">5</td></tr>
            <tr style="border: none;"><td>Table 3.1</td><td>Software Requirements Specification &amp; Stack Versioning</td><td style="text-align: right;">9</td></tr>
            <tr style="border: none;"><td>Table 3.2</td><td>Minimum and Recommended Hardware Specifications</td><td style="text-align: right;">9</td></tr>
            <tr style="border: none;"><td>Table 3.3</td><td>Relational Database Schema: Drivers &amp; Vehicles Tables</td><td style="text-align: right;">10</td></tr>
            <tr style="border: none;"><td>Table 3.4</td><td>Relational Database Schema: Locations &amp; Chargers Tables</td><td style="text-align: right;">11</td></tr>
            <tr style="border: none;"><td>Table 3.5</td><td>Relational Database Schema: Bookings &amp; Charging Sessions Tables</td><td style="text-align: right;">11</td></tr>
            <tr style="border: none;"><td>Table 3.6</td><td>Relational Database Schema: Payments, Loyalty &amp; Reviews Tables</td><td style="text-align: right;">12</td></tr>
            <tr style="border: none;"><td>Table 4.1</td><td>Mapping of Object-Oriented Programming Principles in EvChargingApp</td><td style="text-align: right;">17</td></tr>
            <tr style="border: none;"><td>Table 4.2</td><td>Vehicle Connector vs. Charger Compatibility Matrix</td><td style="text-align: right;">19</td></tr>
            <tr style="border: none;"><td>Table 5.1</td><td>Color Palette &amp; Typography Tokens in AppTheme Architecture</td><td style="text-align: right;">21</td></tr>
            <tr style="border: none;"><td>Table 6.1</td><td>Automated Test Suite Verification Matrix (21 Assertions)</td><td style="text-align: right;">26</td></tr>
        </tbody>
    </table>
</div>

<!-- ========================================== -->
<!-- CHAPTER 1: INTRODUCTION                    -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 1<br>INTRODUCTION</h1>

<p>The global transition from internal combustion engine (ICE) vehicles to clean, battery-powered electric vehicles (EVs) has emerged as one of the pivotal technological and environmental movements of the twenty-first century. As automotive manufacturers accelerate the deployment of high-capacity lithium-ion battery fleets, the viability and mainstream adoption of electric mobility depend entirely on the presence of dependable, responsive, and intelligently orchestrated charging station networks. Unlike conventional hydrocarbon refueling—which requires merely a few minutes per vehicle—electric charging requires significantly longer dwell periods ranging from 20 minutes for high-power DC fast chargers to several hours for standard AC level-2 equipment. This fundamental operational characteristic necessitates sophisticated software architectures to optimize charging bay utilization, prevent grid and station congestion, and provide transparent driver experiences.</p>

<h2 class="section-heading">1.1 Background and Domain Context</h2>
<p>In modern urban ecosystems, the charging infrastructure comprises decentralized charging points distributed across residential complexes, commercial shopping hubs, dedicated highway rest stops, and fleet depots. Each charging station may host multiple chargers featuring heterogeneous technical specifications, including distinct physical connector form-factors (such as Combined Charging System 2 [CCS2], Type 2 AC, CHAdeMO, and GB/T), diverse power ratings (ranging from 7.4 kW to 150 kW DC fast chargers), and dynamic energy tariffs. Drivers navigating these ecosystems experience substantial friction, commonly known as "range anxiety" and "charger availability anxiety." When drivers arrive at an unmanaged or poorly coordinated charging facility, they frequently encounter occupied bays, out-of-order equipment, incompatible connector standards, or lengthy queues without any visibility into dispatch order or estimated waiting durations.</p>

<h2 class="section-heading">1.2 Motivation and Industry Challenges</h2>
<p>The primary motivation for developing the <strong>EV Charging Management System (EvChargingApp)</strong> arises from the evident operational inefficiencies observed in existing commercial EV charging installations. Current market deployments often suffer from critical architectural weaknesses:</p>
<ul>
    <li><strong>Lack of Connector Compatibility Guards:</strong> Many legacy booking applications fail to cross-check the physical plug compatibility of a vehicle against the target charger before accepting a reservation, resulting in drivers reaching charging bays only to discover their inlet cannot physically mate with the dispensing gun.</li>
    <li><strong>First-Come, First-Served (FCFS) Inequities:</strong> Traditional queues process reservations on a rigid temporal FIFO basis. Consequently, a vehicle with an acute emergency (e.g., medical transport or critical commercial fleet delivery) or a vehicle on the verge of total battery depletion (e.g., 5% SoC) is forced to wait behind vehicles that merely require top-up charging from 75% to 80%.</li>
    <li><strong>Opaque Session Billing:</strong> Discrepancies between dispensed energy (measured in kWh) and billed amounts create trust deficits between station operators and end-users.</li>
    <li><strong>Fragmented Loyalty and Review Workflows:</strong> Customer engagement and station rating systems are frequently unverified, allowing fraudulent or irrelevant reviews to distort station quality indices.</li>
</ul>

<h2 class="section-heading">1.3 Problem Statement</h2>
<p>The core objective of this engineering initiative is to design and implement a robust, maintainable, and fully integrated desktop application using <strong>Java 21</strong>, <strong>Object-Oriented Programming (OOP)</strong> paradigms, and a <strong>MySQL</strong> relational database that coordinates charging station infrastructure, resolves queue bottlenecks dynamically, enforces physical hardware compatibility, provides automated session metering and billing, tracks multi-method financial payments, administers loyalty incentives, and captures verified customer feedback within an intuitive graphical interface.</p>

<h2 class="section-heading">1.4 Project Objectives</h2>
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
    <li><strong>Comprehensive Automated Testing:</strong> Develop an automated test harness (<code>AppTestSuite</code>) validating scheduling, compatibility, loyalty logic, lifecycle transitions, and live MySQL transactions.</li>
</ol>

<h2 class="section-heading">1.5 Scope and Operational Boundaries</h2>
<p>The operational scope of the current release encompasses an enterprise-grade desktop deployment suitable for station operators, depot dispatchers, and charging hub administrators. The system interfaces directly with a local or network-hosted MySQL database server. While hardware communication with physical EVSE (Electric Vehicle Supply Equipment) microcontrollers via OCPP (Open Charge Point Protocol) is outside the immediate scope of this desktop software course project, the system provides high-fidelity simulated telemetry streams that replicate real-world sensor streams with high mathematical precision.</p>

<h2 class="section-heading">1.6 Organization of the Report</h2>
<p>The remainder of this project report is organized as follows: <strong>Chapter 2</strong> reviews relevant literature and compares the proposed architecture with conventional approaches. <strong>Chapter 3</strong> details the software requirements specifications, hardware requirements, and the relational database schema. <strong>Chapter 4</strong> elaborates on the system architecture, UML class relationships, core OOP principles, and algorithmic designs. <strong>Chapter 5</strong> provides an exhaustive walkthrough of the user interface screens and component layouts. <strong>Chapter 6</strong> documents the automated test harness, execution matrices, and validation results. <strong>Chapter 7</strong> summarizes project deliverables, pedagogical outcomes, and future research directions, followed by <strong>Chapter 8</strong> which provides formal academic and industry references.</p>

<!-- ========================================== -->
<!-- CHAPTER 2: LITERATURE REVIEW               -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 2<br>LITERATURE REVIEW &amp; SYSTEM COMPARISON</h1>

<p>The rapid evolution of electromobility has catalyzed substantial academic and industrial research into charging scheduling, smart grid integration, and distributed energy transaction management. This chapter examines existing literature, surveys current commercial platforms, analyzes their critical limitations, and articulates the unique value proposition and methodology of the <strong>EvChargingApp</strong> platform.</p>

<h2 class="section-heading">2.1 Survey of Existing EV Infrastructure Platforms</h2>
<p>Contemporary charging systems fall broadly into two operational archetypes: monolithic station-side firmware controllers and cloud-based mobile aggregators. Global networks such as ChargePoint, Tesla Supercharger, and EVgo offer proprietary mobile and cloud experiences. However, examining their structural behavior reveals notable gaps in desktop-based operational dispatching and decentralized station governance:</p>
<ul>
    <li><strong>Centralized Cloud Dependency:</strong> Many commercial networks mandate continuous high-speed cloud synchronization. In network-constrained depot environments or underground parking hubs, local dispatchers lack an autonomous, robust desktop console to manage queue priorities and bay allocations independently of external latency.</li>
    <li><strong>Rigid FIFO Scheduling:</strong> Academic investigations by Cao et al. (2021) and Tan et al. (2022) into queue theory for charging depots reveal that standard First-In-First-Out (FIFO) queuing models lead to severe battery starvation for critically depleted vehicles and introduce dangerous delays for emergency responder fleets.</li>
    <li><strong>Decoupled Loyalty &amp; Feedback:</strong> Commercial systems typically outsource customer feedback to third-party maps or review engines, creating vulnerability to spoofed ratings from non-patrons.</li>
</ul>

<h2 class="section-heading">2.2 Limitations of Conventional Management Approaches</h2>
<p>A rigorous comparative analysis between conventional approaches and the proposed <strong>EvChargingApp</strong> is summarized in Table 2.1.</p>

<div class="table-wrapper">
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
                <td>Dynamic real-time calculation: \\( \text{Total} = \text{Energy (kWh)} \times \text{Rate} \\).</td>
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
</div>

<h2 class="section-heading">2.3 Proposed Solution Architecture &amp; Distinct Advantages</h2>
<p>The proposed <strong>EvChargingApp</strong> resolves the documented bottlenecks through a structured, multi-tier software architecture engineered specifically to illustrate object-oriented principles. By introducing strict domain validation in the service layer, the application ensures that invalid states—such as attempting to charge a CCS2 vehicle using a Type 2 connector or submitting a review for a cancelled booking—are physically impossible within the code. Furthermore, the priority queue abstraction provides mathematical determinism in dispatching vehicles, substantially reducing station turnaround time and prioritizing emergency vehicles.</p>

<h2 class="section-heading">2.4 Software Development Methodology</h2>
<p>The project adhered to an <strong>Iterative Object-Oriented Software Lifecycle (OOSLC)</strong> combining domain modeling, test-driven validation, and agile GUI refactoring. The lifecycle comprises five iterative phases as illustrated in Figure 2.1:</p>
<ol>
    <li><strong>Domain Analysis &amp; Entity Identification:</strong> Identifying real-world physical entities (Driver, Vehicle, Charging Location, Charger) and conceptual entities (Booking, Session, Payment, Review) and drafting their formal attribute boundaries.</li>
    <li><strong>Relational Schema Normalization:</strong> Mapping domain objects into a 3rd Normal Form (3NF) relational schema with referential integrity constraints in MySQL.</li>
    <li><strong>Service &amp; DAO Layer Engineering:</strong> Writing thread-safe JDBC connection handlers, Data Access Objects, and business services encapsulating validation logic.</li>
    <li><strong>Component-Driven GUI Implementation:</strong> Constructing responsive Swing panels using modern UI design tokens (<code>AppTheme</code>) and unifying them via <code>CardLayout</code>.</li>
    <li><strong>Automated Test Suite Verification:</strong> Executing automated unit and integration tests (<code>AppTestSuite</code>) across 21 discrete test scenarios.</li>
</ol>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 110" width="600" height="100" xmlns="http://www.w3.org/2000/svg">
            <defs>
                <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" style="stop-color:#0b3c5d;stop-opacity:1" />
                    <stop offset="100%" style="stop-color:#1d2731;stop-opacity:1" />
                </linearGradient>
            </defs>
            <!-- Phase 1 -->
            <rect x="10" y="25" width="105" height="55" rx="6" fill="url(#grad1)" stroke="#328cc1" stroke-width="2"/>
            <text x="62" y="48" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Phase 1</text>
            <text x="62" y="65" fill="#f9f9f9" font-family="Times New Roman" font-size="9" text-anchor="middle">Domain Modeling</text>
            
            <line x1="115" y1="52" x2="135" y2="52" stroke="#0b3c5d" stroke-width="2" marker-end="url(#arrow)"/>
            <polygon points="135,52 128,48 128,56" fill="#0b3c5d"/>

            <!-- Phase 2 -->
            <rect x="138" y="25" width="105" height="55" rx="6" fill="url(#grad1)" stroke="#328cc1" stroke-width="2"/>
            <text x="190" y="48" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Phase 2</text>
            <text x="190" y="65" fill="#f9f9f9" font-family="Times New Roman" font-size="9" text-anchor="middle">MySQL Schema</text>

            <polygon points="263,52 256,48 256,56" fill="#0b3c5d"/>

            <!-- Phase 3 -->
            <rect x="266" y="25" width="105" height="55" rx="6" fill="url(#grad1)" stroke="#328cc1" stroke-width="2"/>
            <text x="318" y="48" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Phase 3</text>
            <text x="318" y="65" fill="#f9f9f9" font-family="Times New Roman" font-size="9" text-anchor="middle">DAO &amp; Services</text>

            <polygon points="391,52 384,48 384,56" fill="#0b3c5d"/>

            <!-- Phase 4 -->
            <rect x="394" y="25" width="105" height="55" rx="6" fill="url(#grad1)" stroke="#328cc1" stroke-width="2"/>
            <text x="446" y="48" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Phase 4</text>
            <text x="446" y="65" fill="#f9f9f9" font-family="Times New Roman" font-size="9" text-anchor="middle">Swing UI Layouts</text>

            <polygon points="519,52 512,48 512,56" fill="#0b3c5d"/>

            <!-- Phase 5 -->
            <rect x="522" y="25" width="115" height="55" rx="6" fill="url(#grad1)" stroke="#328cc1" stroke-width="2"/>
            <text x="579" y="48" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Phase 5</text>
            <text x="579" y="65" fill="#f9f9f9" font-family="Times New Roman" font-size="9" text-anchor="middle">Test Suite (21 Tests)</text>
        </svg>
    </div>
    <div class="figure-title">Figure 2.1: Iterative Object-Oriented Development Life Cycle</div>
</div>

<!-- ========================================== -->
<!-- CHAPTER 3: SYSTEM REQUIREMENTS             -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 3<br>SYSTEM REQUIREMENTS &amp; DATABASE ARCHITECTURE</h1>

<p>Engineering an enterprise-grade software system necessitates rigorous specification of functional capabilities, performance standards, runtime requirements, and persistent storage models. This chapter details the comprehensive requirements specification and provides the complete architectural design of the relational database.</p>

<h2 class="section-heading">3.1 Functional Requirements Specification</h2>
<p>The functional requirements define the explicit operational behaviors and data processing pipelines implemented in the application:</p>
<ol>
    <li><strong>Driver &amp; Vehicle Profile Management (FR-01):</strong> The system shall record driver credentials (name, phone, email) with uniqueness constraints and register vehicle technical parameters including total battery capacity (kWh), real-time battery percentage (0–100%), and physical connector specification.</li>
    <li><strong>Charging Infrastructure Directory (FR-02):</strong> The system shall catalog charging stations, geolocation addresses, station verification status, star ratings, and location amenities (e.g., Coffee Shop, Restroom, Wi-Fi), alongside individual charger units detailing mode (Fast, Ultra-Fast, Standard), price per kWh, and live availability status.</li>
    <li><strong>Connector Compatibility Verification (FR-03):</strong> The system shall perform case-insensitive cross-checks between the vehicle connector type and the charger connector type prior to permitting a booking.</li>
    <li><strong>Priority Queue Booking Dispatching (FR-04):</strong> The system shall maintain an in-memory priority queue that dispatches <em>EMERGENCY</em> requests ahead of all <em>NORMAL</em> bookings and breaks ties among normal bookings by prioritizing vehicles with the lowest battery percentage.</li>
    <li><strong>Charging Session Metering &amp; Billing (FR-05):</strong> The system shall initialize charging sessions, record dispensed electrical energy in kWh, and automatically compute total invoice amounts based on charger tariff rates upon session completion.</li>
    <li><strong>Multi-Channel Payment &amp; Audit Trail (FR-06):</strong> The system shall support payment reconciliation across UPI, Card, Wallet, and Cash channels, updating transaction status from <em>PENDING</em> to <em>PAID</em>.</li>
    <li><strong>Loyalty Points Accrual &amp; Redemption (FR-07):</strong> The system shall calculate loyalty points at an exact ratio of 1 point per ₹10 paid, update driver accounts, and record audited transaction entries (<em>EARNED</em> / <em>REDEEMED</em>).</li>
    <li><strong>Verified Customer Review Governance (FR-08):</strong> The system shall validate that a review is submitted exclusively for a <em>COMPLETED</em> booking with a rating strictly bounded between 1 and 5 stars.</li>
    <li><strong>Interactive Database Diagnostics (FR-09):</strong> The system shall provide an administrative database console allowing real-time connection testing, sample queries, and direct inspection of table records.</li>
</ol>

<h2 class="section-heading">3.2 Non-Functional Requirements Specification</h2>
<p>Non-functional requirements guarantee the architectural quality, reliability, and security of the platform:</p>
<ul>
    <li><strong>Performance &amp; Responsiveness (NFR-01):</strong> GUI interactions and database operations shall complete within sub-second latencies (< 300 ms for local transactions), and priority queue polling shall execute in \\( O(\log N) \\) computational complexity.</li>
    <li><strong>Data Integrity &amp; ACID Guarantees (NFR-02):</strong> Persistent storage operations shall adhere strictly to Atomicity, Consistency, Isolation, and Durability through MySQL InnoDB transactional storage.</li>
    <li><strong>Maintainability &amp; Clean Architecture (NFR-03):</strong> Code shall follow strict Java conventions, modular packaging, complete getter/setter encapsulation, and clean separation between GUI and database code.</li>
    <li><strong>User Interface Usability (NFR-04):</strong> The visual layout shall employ responsive cards, clear typographical hierarchies, consistent color-coded status badges, and non-blocking toast notifications.</li>
</ul>

<h2 class="section-heading">3.3 Hardware Requirements &amp; Sizing</h2>
<p>The system is architected for deployment across standard workstation hardware. The sizing specifications are detailed in Table 3.2.</p>

<div class="table-wrapper">
    <div class="table-title">Table 3.2: Minimum and Recommended Hardware Specifications</div>
    <table>
        <thead>
            <tr>
                <th>Hardware Component</th>
                <th>Minimum Required Specification</th>
                <th>Recommended Deployment Specification</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Processor (CPU)</strong></td>
                <td>Dual-Core x86_64 / ARM64 @ 2.0 GHz</td>
                <td>Quad-Core Intel Core i5 / AMD Ryzen 5 or higher @ 2.8+ GHz</td>
            </tr>
            <tr>
                <td><strong>Primary Memory (RAM)</strong></td>
                <td>4 GB DDR4</td>
                <td>8 GB to 16 GB DDR4/DDR5</td>
            </tr>
            <tr>
                <td><strong>Storage Capacity</strong></td>
                <td>500 MB free storage for binaries and DB</td>
                <td>10 GB SSD for high-volume telemetry &amp; logs</td>
            </tr>
            <tr>
                <td><strong>Display Resolution</strong></td>
                <td>1280 &times; 720 (HD)</td>
                <td>1920 &times; 1080 (Full HD) or higher</td>
            </tr>
        </tbody>
    </table>
</div>

<h2 class="section-heading">3.4 Software Environment &amp; Technology Stack</h2>
<p>The platform leverages modern, industry-standard development frameworks. The full stack configuration is documented in Table 3.1.</p>

<div class="table-wrapper">
    <div class="table-title">Table 3.1: Software Requirements Specification &amp; Stack Versioning</div>
    <table>
        <thead>
            <tr>
                <th>Layer / Component</th>
                <th>Technology / Library Selected</th>
                <th>Version</th>
                <th>Operational Rationale</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Runtime Platform</strong></td>
                <td>Java Development Kit (JDK)</td>
                <td>Java 21 LTS</td>
                <td>Modern language features, pattern matching, robust concurrency.</td>
            </tr>
            <tr>
                <td><strong>GUI Toolkit</strong></td>
                <td>Java Swing (javax.swing)</td>
                <td>JDK 21 Core</td>
                <td>Native desktop portability, lightweight windowing, custom rendering.</td>
            </tr>
            <tr>
                <td><strong>Relational Database</strong></td>
                <td>MySQL Server (InnoDB Engine)</td>
                <td>8.0 / 9.1</td>
                <td>ACID transactions, foreign keys, check constraints, fast indexing.</td>
            </tr>
            <tr>
                <td><strong>Database Connectivity</strong></td>
                <td>MySQL Connector/J</td>
                <td>9.1.0</td>
                <td>Official high-performance Type-4 JDBC driver for Java applications.</td>
            </tr>
            <tr>
                <td><strong>Build &amp; Dependency Tool</strong></td>
                <td>Apache Maven</td>
                <td>3.9+</td>
                <td>Standardized build lifecycle, reproducible compilation, dependency management.</td>
            </tr>
            <tr>
                <td><strong>IDE Workspace</strong></td>
                <td>Eclipse IDE / VS Code</td>
                <td>2024+</td>
                <td>Integrated debugging, class navigation, automated testing.</td>
            </tr>
        </tbody>
    </table>
</div>

<h2 class="section-heading">3.5 Relational Database Schema &amp; Data Dictionary</h2>
<p>The persistent layer is modeled under 3rd Normal Form (3NF) to eliminate data redundancy and preserve integrity. The database schema (<code>ev_charging_app</code>) comprises 10 interconnected tables defined in Tables 3.3 through 3.6.</p>

<div class="table-wrapper">
    <div class="table-title">Table 3.3: Relational Database Schema: Drivers &amp; Vehicles Tables</div>
    <table>
        <thead>
            <tr>
                <th>Table Name</th>
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
                <td>Unique surrogate identifier for registered drivers.</td>
            </tr>
            <tr>
                <td><code>name</code></td>
                <td>VARCHAR(100)</td>
                <td>NOT NULL</td>
                <td>Full legal name of the driver.</td>
            </tr>
            <tr>
                <td><code>phone_number</code></td>
                <td>VARCHAR(15)</td>
                <td>NOT NULL, UNIQUE</td>
                <td>Mobile contact number for booking notifications.</td>
            </tr>
            <tr>
                <td><code>email</code></td>
                <td>VARCHAR(150)</td>
                <td>NOT NULL, UNIQUE</td>
                <td>Email address for invoice delivery.</td>
            </tr>
            <tr>
                <td><code>loyalty_points</code></td>
                <td>INT</td>
                <td>NOT NULL, DEFAULT 0</td>
                <td>Accumulated loyalty reward point balance.</td>
            </tr>
            <tr>
                <td rowspan="6"><strong>vehicles</strong></td>
                <td><code>vehicle_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Unique surrogate identifier for registered vehicles.</td>
            </tr>
            <tr>
                <td><code>driver_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; drivers(driver_id)</td>
                <td>Owner link with ON DELETE CASCADE.</td>
            </tr>
            <tr>
                <td><code>model</code></td>
                <td>VARCHAR(100)</td>
                <td>NOT NULL</td>
                <td>Vehicle manufacturer and model designation.</td>
            </tr>
            <tr>
                <td><code>battery_capacity_kwh</code></td>
                <td>DECIMAL(5,2)</td>
                <td>NOT NULL</td>
                <td>Total gross battery capacity in kilowatt-hours.</td>
            </tr>
            <tr>
                <td><code>connector_type</code></td>
                <td>VARCHAR(30)</td>
                <td>NOT NULL</td>
                <td>Physical inlet form-factor (CCS2, Type 2, etc.).</td>
            </tr>
            <tr>
                <td><code>current_battery_percentage</code></td>
                <td>INT</td>
                <td>CHECK (between 0 and 100)</td>
                <td>Current State of Charge (SoC) in percent.</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="table-wrapper">
    <div class="table-title">Table 3.4: Relational Database Schema: Locations &amp; Chargers Tables</div>
    <table>
        <thead>
            <tr>
                <th>Table Name</th>
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
                <td>Surrogate identifier for physical charging facilities.</td>
            </tr>
            <tr>
                <td><code>name</code></td>
                <td>VARCHAR(150)</td>
                <td>NOT NULL</td>
                <td>Commercial name of the station or hub.</td>
            </tr>
            <tr>
                <td><code>address</code></td>
                <td>VARCHAR(255)</td>
                <td>NOT NULL</td>
                <td>Complete street address and city.</td>
            </tr>
            <tr>
                <td><code>verified</code></td>
                <td>TINYINT(1)</td>
                <td>NOT NULL, DEFAULT 0</td>
                <td>Verification flag indicating certified inspection.</td>
            </tr>
            <tr>
                <td><code>rating</code></td>
                <td>DECIMAL(2,1)</td>
                <td>CHECK (between 0.0 and 5.0)</td>
                <td>Aggregated historical driver satisfaction rating.</td>
            </tr>
            <tr>
                <td rowspan="6"><strong>chargers</strong></td>
                <td><code>charger_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Unique internal charger identification key.</td>
            </tr>
            <tr>
                <td><code>location_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; locations</td>
                <td>Station host identifier with ON DELETE CASCADE.</td>
            </tr>
            <tr>
                <td><code>charger_code</code></td>
                <td>VARCHAR(30)</td>
                <td>NOT NULL, UNIQUE</td>
                <td>Hardware dispenser label code (e.g., 'GC-01').</td>
            </tr>
            <tr>
                <td><code>charging_mode</code></td>
                <td>VARCHAR(20)</td>
                <td>NOT NULL</td>
                <td>Charging speed category (Fast, Ultra-Fast).</td>
            </tr>
            <tr>
                <td><code>connector_type</code></td>
                <td>VARCHAR(30)</td>
                <td>NOT NULL</td>
                <td>Physical connector plug provided by dispenser.</td>
            </tr>
            <tr>
                <td><code>price_per_kwh</code></td>
                <td>DECIMAL(8,2)</td>
                <td>CHECK (price_per_kwh &gt; 0)</td>
                <td>Tariff rate charged per kilowatt-hour consumed.</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="table-wrapper">
    <div class="table-title">Table 3.5: Relational Database Schema: Bookings &amp; Charging Sessions Tables</div>
    <table>
        <thead>
            <tr>
                <th>Table Name</th>
                <th>Column Name</th>
                <th>Data Type</th>
                <th>Constraints</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td rowspan="6"><strong>bookings</strong></td>
                <td><code>booking_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Unique booking identification index.</td>
            </tr>
            <tr>
                <td><code>driver_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; drivers</td>
                <td>Driver initiating the reservation.</td>
            </tr>
            <tr>
                <td><code>charger_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; chargers</td>
                <td>Assigned charging dispenser bay.</td>
            </tr>
            <tr>
                <td><code>request_type</code></td>
                <td>ENUM</td>
                <td>'NORMAL', 'EMERGENCY'</td>
                <td>Priority dispatch category.</td>
            </tr>
            <tr>
                <td><code>booking_status</code></td>
                <td>ENUM</td>
                <td>'PENDING','CONFIRMED','CHARGING','COMPLETED','CANCELLED'</td>
                <td>Current lifecycle phase of the booking request.</td>
            </tr>
            <tr>
                <td><code>requested_at</code></td>
                <td>TIMESTAMP</td>
                <td>DEFAULT CURRENT_TIMESTAMP</td>
                <td>Timestamp when booking was submitted.</td>
            </tr>
            <tr>
                <td rowspan="6"><strong>charging_sessions</strong></td>
                <td><code>session_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Unique charging session identifier.</td>
            </tr>
            <tr>
                <td><code>booking_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; bookings, UNIQUE</td>
                <td>One-to-one link to parent booking with CASCADE.</td>
            </tr>
            <tr>
                <td><code>started_at</code></td>
                <td>DATETIME</td>
                <td>NULL</td>
                <td>Exact timestamp when energy flow commenced.</td>
            </tr>
            <tr>
                <td><code>ended_at</code></td>
                <td>DATETIME</td>
                <td>NULL</td>
                <td>Exact timestamp when charging terminated.</td>
            </tr>
            <tr>
                <td><code>energy_used_kwh</code></td>
                <td>DECIMAL(8,2)</td>
                <td>DEFAULT NULL</td>
                <td>Total electrical energy dispensed in kWh.</td>
            </tr>
            <tr>
                <td><code>total_amount</code></td>
                <td>DECIMAL(10,2)</td>
                <td>DEFAULT NULL</td>
                <td>Computed financial invoice charge (₹).</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="table-wrapper">
    <div class="table-title">Table 3.6: Relational Database Schema: Payments, Loyalty &amp; Reviews Tables</div>
    <table>
        <thead>
            <tr>
                <th>Table Name</th>
                <th>Column Name</th>
                <th>Data Type</th>
                <th>Constraints</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td rowspan="5"><strong>payments</strong></td>
                <td><code>payment_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Surrogate identifier for settlement records.</td>
            </tr>
            <tr>
                <td><code>session_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; charging_sessions, UNIQUE</td>
                <td>Associated session with ON DELETE CASCADE.</td>
            </tr>
            <tr>
                <td><code>payment_method</code></td>
                <td>ENUM</td>
                <td>'UPI', 'CARD', 'CASH', 'WALLET'</td>
                <td>Payment channel utilized for transaction.</td>
            </tr>
            <tr>
                <td><code>amount</code></td>
                <td>DECIMAL(10,2)</td>
                <td>NOT NULL</td>
                <td>Total amount settled in currency units.</td>
            </tr>
            <tr>
                <td><code>payment_status</code></td>
                <td>ENUM</td>
                <td>'PENDING', 'PAID', 'FAILED'</td>
                <td>Financial settlement status indicator.</td>
            </tr>
            <tr>
                <td rowspan="4"><strong>loyalty_transactions</strong></td>
                <td><code>loyalty_transaction_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Audit record identifier for reward points.</td>
            </tr>
            <tr>
                <td><code>driver_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; drivers</td>
                <td>Driver receiving or redeeming reward points.</td>
            </tr>
            <tr>
                <td><code>points</code></td>
                <td>INT</td>
                <td>CHECK (points &gt; 0)</td>
                <td>Number of points added or subtracted.</td>
            </tr>
            <tr>
                <td><code>transaction_type</code></td>
                <td>ENUM</td>
                <td>'EARNED', 'REDEEMED'</td>
                <td>Classification of point transaction ledger entry.</td>
            </tr>
            <tr>
                <td rowspan="4"><strong>reviews</strong></td>
                <td><code>review_id</code></td>
                <td>INT</td>
                <td>PRIMARY KEY, AUTO_INCREMENT</td>
                <td>Unique identifier for customer reviews.</td>
            </tr>
            <tr>
                <td><code>booking_id</code></td>
                <td>INT</td>
                <td>FOREIGN KEY &rarr; bookings, UNIQUE</td>
                <td>Guarantees exactly one review per booking.</td>
            </tr>
            <tr>
                <td><code>rating</code></td>
                <td>INT</td>
                <td>CHECK (rating between 1 and 5)</td>
                <td>Integer star satisfaction rating.</td>
            </tr>
            <tr>
                <td><code>comment</code></td>
                <td>VARCHAR(500)</td>
                <td>NULL</td>
                <td>Descriptive feedback text submitted by driver.</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 160" width="620" height="150" xmlns="http://www.w3.org/2000/svg">
            <!-- Entities -->
            <rect x="10" y="10" width="90" height="40" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="55" y="34" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">DRIVERS</text>

            <rect x="140" y="10" width="90" height="40" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="185" y="34" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">VEHICLES</text>

            <rect x="270" y="10" width="100" height="40" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="320" y="34" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">BOOKINGS</text>

            <rect x="410" y="10" width="100" height="40" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="460" y="34" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">CHARGERS</text>

            <rect x="540" y="10" width="100" height="40" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="590" y="34" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">LOCATIONS</text>

            <!-- Bottom Tier -->
            <rect x="270" y="90" width="100" height="40" rx="4" fill="#1d2731" stroke="#328cc1" stroke-width="1.5"/>
            <text x="320" y="114" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">SESSIONS</text>

            <rect x="140" y="90" width="90" height="40" rx="4" fill="#1d2731" stroke="#328cc1" stroke-width="1.5"/>
            <text x="185" y="114" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">PAYMENTS</text>

            <rect x="10" y="90" width="90" height="40" rx="4" fill="#1d2731" stroke="#328cc1" stroke-width="1.5"/>
            <text x="55" y="114" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">LOYALTY</text>

            <rect x="410" y="90" width="100" height="40" rx="4" fill="#1d2731" stroke="#328cc1" stroke-width="1.5"/>
            <text x="460" y="114" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">REVIEWS</text>

            <!-- Connectors -->
            <line x1="100" y1="30" x2="140" y2="30" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="230" y1="30" x2="270" y2="30" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="370" y1="30" x2="410" y2="30" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="510" y1="30" x2="540" y2="30" stroke="#0b3c5d" stroke-width="1.5"/>
            
            <line x1="320" y1="50" x2="320" y2="90" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="270" y1="110" x2="230" y2="110" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="140" y1="110" x2="100" y2="110" stroke="#0b3c5d" stroke-width="1.5"/>
            <line x1="370" y1="110" x2="410" y2="110" stroke="#0b3c5d" stroke-width="1.5"/>
        </svg>
    </div>
    <div class="figure-title">Figure 3.1: Entity Relationship (ER) Diagram of ev_charging_app Database</div>
</div>

<!-- ========================================== -->
<!-- CHAPTER 4: SYSTEM DESIGN & OOP CONCEPTS    -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 4<br>SYSTEM DESIGN &amp; OBJECT-ORIENTED PROGRAMMING CONCEPTS</h1>

<p>Object-Oriented Programming (OOP) is a software engineering paradigm predicated upon representing computational domains as collections of communicating objects, each encapsulating discrete state and behavioral contracts. This chapter provides a rigorous examination of the multi-tier architectural design of <strong>EvChargingApp</strong>, its structural class hierarchies, and how fundamental OOP tenets are realized throughout the implementation.</p>

<h2 class="section-heading">4.1 Multi-Tier MVC &amp; DAO Architectural Design</h2>
<p>To eliminate coupling between graphical components and persistence logic, the system adopts a multi-tiered architecture marrying the <strong>Model-View-Controller (MVC)</strong> design pattern with the <strong>Data Access Object (DAO)</strong> abstraction pattern. Figure 4.1 illustrates the structural segregation across layers.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 170" width="620" height="160" xmlns="http://www.w3.org/2000/svg">
            <!-- Tier 1: Presentation -->
            <rect x="20" y="10" width="610" height="38" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.5"/>
            <text x="325" y="26" fill="#ffffff" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">PRESENTATION TIER (VIEW / CONTROLLER)</text>
            <text x="325" y="41" fill="#e0e0e0" font-family="Times New Roman" font-size="9" text-anchor="middle">MainFrame, DashboardPanel, DriverVehiclePanel, ChargerPanel, BookingPanel, SessionPanel, PaymentPanel, ReviewPanel</text>

            <!-- Tier 2: Service / Business Logic -->
            <rect x="20" y="60" width="610" height="38" rx="4" fill="#1d2731" stroke="#328cc1" stroke-width="1.5"/>
            <text x="325" y="76" fill="#ffffff" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">SERVICE &amp; BUSINESS LOGIC TIER</text>
            <text x="325" y="91" fill="#e0e0e0" font-family="Times New Roman" font-size="9" text-anchor="middle">BookingService (Validation &amp; State Machine), BookingQueue (PriorityQueue &amp; Comparator), LoyaltyService</text>

            <!-- Tier 3: Data Access Layer -->
            <rect x="20" y="110" width="295" height="42" rx="4" fill="#328cc1" stroke="#0b3c5d" stroke-width="1.5"/>
            <text x="167" y="127" fill="#ffffff" font-family="Times New Roman" font-size="10.5" font-weight="bold" text-anchor="middle">DATA ACCESS LAYER (DAO)</text>
            <text x="167" y="142" fill="#f0f0f0" font-family="Times New Roman" font-size="8.5" text-anchor="middle">DriverDAO, VehicleDAO, ChargerDAO, BookingDAO, SessionDAO, PaymentDAO, LoyaltyDAO, ReviewDAO</text>

            <!-- Tier 4: Relational DB -->
            <rect x="335" y="110" width="295" height="42" rx="4" fill="#d9b310" stroke="#b38f00" stroke-width="1.5"/>
            <text x="482" y="127" fill="#111111" font-family="Times New Roman" font-size="10.5" font-weight="bold" text-anchor="middle">PERSISTENCE TIER (MySQL ENGINE)</text>
            <text x="482" y="142" fill="#222222" font-family="Times New Roman" font-size="8.5" text-anchor="middle">DBConnection Pool, JDBC 9.1.0, 10 Relational Tables, Constraints, Triggers</text>
        </svg>
    </div>
    <div class="figure-title">Figure 4.1: Multi-Tier MVC and DAO Software Architecture</div>
</div>

<h2 class="section-heading">4.2 Class Hierarchy &amp; Domain Modeling</h2>
<p>The structural core of <strong>EvChargingApp</strong> consists of eight primary domain entities located within the <code>com.evcharge.app.model</code> package. Figure 4.2 illustrates the UML relationships connecting these domain classes.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 200" width="620" height="190" xmlns="http://www.w3.org/2000/svg">
            <!-- Driver Box -->
            <rect x="10" y="10" width="130" height="80" fill="#fff" stroke="#000" stroke-width="1"/>
            <text x="75" y="24" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Driver</text>
            <line x1="10" y1="28" x2="140" y2="28" stroke="#000"/>
            <text x="15" y="40" font-family="Courier New" font-size="8">-driverId: int</text>
            <text x="15" y="52" font-family="Courier New" font-size="8">-name: String</text>
            <text x="15" y="64" font-family="Courier New" font-size="8">-loyaltyPoints: int</text>
            <line x1="10" y1="68" x2="140" y2="68" stroke="#000"/>
            <text x="15" y="80" font-family="Courier New" font-size="8">+addLoyaltyPoints()</text>

            <!-- Vehicle Box -->
            <rect x="10" y="110" width="130" height="80" fill="#fff" stroke="#000" stroke-width="1"/>
            <text x="75" y="124" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Vehicle</text>
            <line x1="10" y1="128" x2="140" y2="128" stroke="#000"/>
            <text x="15" y="140" font-family="Courier New" font-size="8">-connectorType: String</text>
            <text x="15" y="152" font-family="Courier New" font-size="8">-batteryPct: int</text>
            <text x="15" y="164" font-family="Courier New" font-size="8">-capacityKwh: double</text>
            <line x1="10" y1="168" x2="140" y2="168" stroke="#000"/>
            <text x="15" y="180" font-family="Courier New" font-size="8">+getConnectorType()</text>

            <!-- Booking Box -->
            <rect x="250" y="50" width="150" height="95" fill="#fff" stroke="#000" stroke-width="1.5"/>
            <text x="325" y="65" font-family="Times New Roman" font-size="10.5" font-weight="bold" text-anchor="middle">Booking</text>
            <line x1="250" y1="70" x2="400" y2="70" stroke="#000"/>
            <text x="255" y="82" font-family="Courier New" font-size="8">-bookingId: String</text>
            <text x="255" y="94" font-family="Courier New" font-size="8">-driver: Driver</text>
            <text x="255" y="106" font-family="Courier New" font-size="8">-vehicle: Vehicle</text>
            <text x="255" y="118" font-family="Courier New" font-size="8">-status: String</text>
            <line x1="250" y1="122" x2="400" y2="122" stroke="#000"/>
            <text x="255" y="134" font-family="Courier New" font-size="8">+confirmBooking()</text>

            <!-- Charger Box -->
            <rect x="490" y="10" width="145" height="80" fill="#fff" stroke="#000" stroke-width="1"/>
            <text x="562" y="24" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Charger</text>
            <line x1="490" y1="28" x2="635" y2="28" stroke="#000"/>
            <text x="495" y="40" font-family="Courier New" font-size="8">-chargerId: String</text>
            <text x="495" y="52" font-family="Courier New" font-size="8">-connectorType: String</text>
            <text x="495" y="64" font-family="Courier New" font-size="8">-available: boolean</text>
            <line x1="490" y1="68" x2="635" y2="68" stroke="#000"/>
            <text x="495" y="80" font-family="Courier New" font-size="8">+isCompatibleWith()</text>

            <!-- Session Box -->
            <rect x="490" y="110" width="145" height="80" fill="#fff" stroke="#000" stroke-width="1"/>
            <text x="562" y="124" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">ChargingSession</text>
            <line x1="490" y1="128" x2="635" y2="128" stroke="#000"/>
            <text x="495" y="140" font-family="Courier New" font-size="8">-energyUsedKwh: double</text>
            <text x="495" y="152" font-family="Courier New" font-size="8">-totalAmount: double</text>
            <line x1="490" y1="168" x2="635" y2="168" stroke="#000"/>
            <text x="495" y="180" font-family="Courier New" font-size="8">+completeSession()</text>

            <!-- Associations -->
            <line x1="140" y1="50" x2="250" y2="80" stroke="#000" stroke-width="1.2"/>
            <text x="190" y="60" font-family="Times New Roman" font-size="9">1</text>
            <text x="235" y="75" font-family="Times New Roman" font-size="9">0..*</text>

            <line x1="140" y1="150" x2="250" y2="115" stroke="#000" stroke-width="1.2"/>
            <text x="190" y="145" font-family="Times New Roman" font-size="9">1</text>
            <text x="235" y="125" font-family="Times New Roman" font-size="9">0..*</text>

            <line x1="490" y1="50" x2="400" y2="80" stroke="#000" stroke-width="1.2"/>
            <text x="450" y="60" font-family="Times New Roman" font-size="9">1</text>
            <text x="415" y="75" font-family="Times New Roman" font-size="9">0..*</text>

            <line x1="400" y1="115" x2="490" y2="150" stroke="#000" stroke-width="1.2"/>
            <text x="415" y="125" font-family="Times New Roman" font-size="9">1</text>
            <text x="475" y="145" font-family="Times New Roman" font-size="9">1</text>
        </svg>
    </div>
    <div class="figure-title">Figure 4.2: UML Class Diagram of EvChargingApp Domain Entities</div>
</div>

<h2 class="section-heading">4.3 Demonstration of Core Object-Oriented Principles</h2>
<p>The system was engineered as an authentic realization of core object-oriented programming concepts. Table 4.1 summarizes the mapping between OOP tenets and their physical realization in the codebase.</p>

<div class="table-wrapper">
    <div class="table-title">Table 4.1: Mapping of Object-Oriented Programming Principles in EvChargingApp</div>
    <table>
        <thead>
            <tr>
                <th>OOP Principle</th>
                <th>Architectural Manifestation in EvChargingApp</th>
                <th>Primary Implementing Class(es)</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Encapsulation</strong></td>
                <td>Private state fields guarded by public accessor/mutator methods; strict domain invariant enforcement preventing illegal values (e.g., negative battery or invalid ratings).</td>
                <td><code>Driver</code>, <code>Vehicle</code>, <code>Charger</code>, <code>Booking</code>, <code>Payment</code></td>
            </tr>
            <tr>
                <td><strong>Abstraction</strong></td>
                <td>Data Access Object (DAO) pattern abstracts underlying SQL queries, connection pooling, and result set mapping away from service and GUI layers.</td>
                <td><code>DBConnection</code>, <code>BookingDAO</code>, <code>PaymentDAO</code></td>
            </tr>
            <tr>
                <td><strong>Polymorphism</strong></td>
                <td>Runtime dynamic dispatch via functional comparator interfaces (<code>Comparator&lt;Booking&gt;</code>) in PriorityQueue; method overloading in model constructors.</td>
                <td><code>BookingQueue</code>, <code>AppTestSuite</code></td>
            </tr>
            <tr>
                <td><strong>Composition &amp; Aggregation</strong></td>
                <td>Complex domain entities composed of discrete collaborating objects (e.g., Booking aggregates Driver, Vehicle, and Charger; Session references Booking).</td>
                <td><code>Booking</code>, <code>ChargingSession</code>, <code>Review</code>, <code>Payment</code></td>
            </tr>
            <tr>
                <td><strong>Separation of Concerns</strong></td>
                <td>Decoupling UI view presentation from business decision logic and persistence services.</td>
                <td><code>MainFrame</code>, <code>BookingService</code>, <code>LoyaltyService</code></td>
            </tr>
        </tbody>
    </table>
</div>

<h3 class="sub-section-heading">4.3.1 Encapsulation and Data Integrity</h3>
<p>Encapsulation ensures that an object maintains strict sovereignty over its internal state. In <code>Driver.java</code> and <code>Vehicle.java</code>, all attributes—such as <code>loyaltyPoints</code>, <code>batteryCapacityKwh</code>, and <code>currentBatteryPercentage</code>—are explicitly declared <code>private</code>. State modifications are permitted solely through dedicated behavioral methods that validate parameters against physical laws. For example, points cannot be added if the value is non-positive:</p>

<div class="code-block">public void addLoyaltyPoints(int points) {
    if (points > 0) {
        this.loyaltyPoints += points;
    }
}</div>

<h3 class="sub-section-heading">4.3.2 Abstraction Through the DAO Pattern</h3>
<p>Abstraction conceals complex low-level implementation details behind clean, high-level interfaces. The presentation panels never execute raw SQL statements, manage JDBC connections, or parse <code>ResultSet</code> rows directly. Instead, they interact with Data Access Objects (such as <code>ChargerDAO</code> and <code>BookingDAO</code>) that expose clean domain operations like <code>updateAvailability(int chargerId, boolean available)</code>. The singleton-managed <code>DBConnection.java</code> encapsulates thread-safe connection acquisition, ensuring database driver nuances remain invisible to the client layers.</p>

<h3 class="sub-section-heading">4.3.3 Polymorphic Priority Dispatching</h3>
<p>Polymorphism is manifested in both static and dynamic forms. Static polymorphism is achieved through comprehensive constructor overloading across all domain entities, allowing objects to be instantiated in test mode (without primary keys) or in persistence mode (with autoincremented MySQL keys). Dynamic polymorphism is illustrated through Java's functional interface dispatching in <code>BookingQueue.java</code>, where a custom lambda implementing <code>java.util.Comparator&lt;Booking&gt;</code> is supplied to <code>java.util.PriorityQueue</code> at runtime.</p>

<h3 class="sub-section-heading">4.3.4 Composition and Object Relationships</h3>
<p>The domain model emphasizes composition over inheritance to model real-world relationships accurately without creating fragile class hierarchies. A <code>Booking</code> object is composed of a <code>Driver</code>, a <code>Vehicle</code>, and a <code>Charger</code>. A <code>ChargingSession</code> maintains a direct composition link to its originating <code>Booking</code>, and a <code>Payment</code> encapsulates the resulting <code>ChargingSession</code>. This composition web ensures referential navigation while preserving independent entity lifecycles.</p>

<h2 class="section-heading">4.4 Business Logic Algorithms &amp; Mathematical Formulations</h2>
<p>The core intelligence of <strong>EvChargingApp</strong> is encapsulated in three mathematical and rule-based algorithms described below.</p>

<h3 class="sub-section-heading">4.4.1 Priority Queue Scheduling Algorithm</h3>
<p>To maximize station utility and provide humanitarian priority to emergency fleets, the scheduling engine implements a specialized non-preemptive priority queue. Given two bookings \\( B_1 \\) and \\( B_2 \\), the priority comparator function \\( f(B_1, B_2) \\) evaluates as follows:</p>

<div class="code-block">queue = new PriorityQueue<>((firstBooking, secondBooking) -> {
    boolean firstIsEmergency = firstBooking.getChargingMode().equalsIgnoreCase("EMERGENCY");
    boolean secondIsEmergency = secondBooking.getChargingMode().equalsIgnoreCase("EMERGENCY");

    if (firstIsEmergency && !secondIsEmergency) return -1; // first jumps ahead
    if (!firstIsEmergency && secondIsEmergency) return 1;  // second jumps ahead

    // Tiebreaker: Ascending order of current battery percentage (lowest SoC first)
    return Integer.compare(
        firstBooking.getVehicle().getCurrentBatteryPercentage(),
        secondBooking.getVehicle().getCurrentBatteryPercentage()
    );
});</div>

<p>Mathematically, the comparator establishes a lexicographical ordering tuple:</p>
<p style="text-align: center;">\\[ P(B) = \\langle -\\mathbb{I}_{\\text{EMERGENCY}}(B),\\; \\text{SoC}(B) \\rangle \\]</p>
<p>where \\( \\mathbb{I}_{\\text{EMERGENCY}}(B) \\in \\{0, 1\\} \\) is an indicator variable denoting emergency status and \\( \\text{SoC}(B) \\in [0, 100] \\) denotes the vehicle's remaining battery percentage. Figure 4.4 illustrates the decision tree of this comparator.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 140" width="620" height="130" xmlns="http://www.w3.org/2000/svg">
            <rect x="235" y="10" width="180" height="30" rx="4" fill="#0b3c5d" stroke="#328cc1" stroke-width="1.2"/>
            <text x="325" y="29" fill="#ffffff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">Compare Bookings B1 &amp; B2</text>

            <polygon points="325,50 395,80 325,110 255,80" fill="#f4f6f8" stroke="#0b3c5d" stroke-width="1.5"/>
            <text x="325" y="78" font-family="Times New Roman" font-size="9" font-weight="bold" text-anchor="middle">Emergency Mode?</text>
            <text x="325" y="90" font-family="Times New Roman" font-size="8" text-anchor="middle">Different?</text>

            <line x1="255" y1="80" x2="140" y2="80" stroke="#000" stroke-width="1.2"/>
            <rect x="30" y="65" width="110" height="32" rx="4" fill="#d9534f" stroke="#b52b27" stroke-width="1"/>
            <text x="85" y="85" fill="#fff" font-family="Times New Roman" font-size="8.5" font-weight="bold" text-anchor="middle">Emergency Jumps Ahead</text>

            <line x1="395" y1="80" x2="500" y2="80" stroke="#000" stroke-width="1.2"/>
            <rect x="500" y="65" width="130" height="32" rx="4" fill="#328cc1" stroke="#0b3c5d" stroke-width="1"/>
            <text x="565" y="80" fill="#fff" font-family="Times New Roman" font-size="8.5" font-weight="bold" text-anchor="middle">Compare Battery SoC</text>
            <text x="565" y="91" fill="#fff" font-family="Times New Roman" font-size="7.5" text-anchor="middle">Lowest % Dispatched First</text>
        </svg>
    </div>
    <div class="figure-title">Figure 4.4: Priority Queue Comparator Scheduling Logic Flow</div>
</div>

<h3 class="sub-section-heading">4.4.2 Physical Connector Compatibility Engine</h3>
<p>The connector compatibility engine enforces physical hardware constraints by validating the vehicle's connector against the target charger before queue admission. Table 4.2 presents the compatibility truth matrix enforced by <code>Charger.isCompatibleWith(Vehicle vehicle)</code>.</p>

<div class="table-wrapper">
    <div class="table-title">Table 4.2: Vehicle Connector vs. Charger Compatibility Matrix</div>
    <table>
        <thead>
            <tr>
                <th>Vehicle Connector Type</th>
                <th>Charger: CCS2</th>
                <th>Charger: Type 2 AC</th>
                <th>Charger: CHAdeMO</th>
                <th>Charger: GB/T</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>CCS2 (DC Fast / AC)</strong></td>
                <td><strong style="color: green;">COMPATIBLE (TRUE)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
            </tr>
            <tr>
                <td><strong>Type 2 (AC European)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><strong style="color: green;">COMPATIBLE (TRUE)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
            </tr>
            <tr>
                <td><strong>CHAdeMO (DC Asian)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><strong style="color: green;">COMPATIBLE (TRUE)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
            </tr>
            <tr>
                <td><strong>GB/T (Chinese DC)</strong></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><span style="color: red;">INCOMPATIBLE (FALSE)</span></td>
                <td><strong style="color: green;">COMPATIBLE (TRUE)</strong></td>
            </tr>
        </tbody>
    </table>
</div>

<h3 class="sub-section-heading">4.4.3 Metered Consumption &amp; Dynamic Billing Engine</h3>
<p>Upon termination of energy dispensing, the session engine computes invoice charges using a deterministic tariff equation:</p>
<p style="text-align: center;">\\[ \\text{Total Invoice Amount (₹)} = E_{\\text{dispensed}} (\\text{kWh}) \\times R_{\\text{tariff}} (₹/\\text{kWh}) \\]</p>
<p>where \\( E_{\\text{dispensed}} > 0 \\) and \\( R_{\\text{tariff}} \\) is retrieved directly from the linked <code>Charger</code> entity.</p>

<h3 class="sub-section-heading">4.4.4 Loyalty Rewards Program Engine</h3>
<p>To encourage sustained user retention, the loyalty service executes a mathematical award formula upon successful payment confirmation:</p>
<p style="text-align: center;">\\[ P_{\\text{awarded}} = \\left\\lfloor \\frac{\\text{Amount Paid}}{10} \\right\\rfloor \\quad \\forall \\; \\text{PaymentStatus} = \\text{'PAID'} \\]</p>
<p>If payment status is <em>PENDING</em> or <em>FAILED</em>, \\( P_{\\text{awarded}} = 0 \\). Redemptions are validated by verifying that \\( P_{\\text{redeem}} \\le P_{\\text{current}} \\), followed by an atomic decrement and insertion of a <code>REDEEMED</code> audit record in <code>loyalty_transactions</code>.</p>

<h3 class="sub-section-heading">4.4.5 Verified Post-Session Review State Machine</h3>
<p>To eliminate fraudulent feedback, <code>Review.isValidReview()</code> acts as a gatekeeper verifying three mandatory predicates: (1) booking reference is non-null, (2) booking status equals <em>COMPLETED</em>, and (3) rating satisfies \\( 1 \\le \\text{rating} \\le 5 \\). If any predicate fails, persistence to MySQL is rejected.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="600" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="25" width="100" height="40" rx="20" fill="#f0ad4e" stroke="#eea236" stroke-width="1.5"/>
            <text x="70" y="49" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">PENDING</text>

            <line x1="120" y1="45" x2="170" y2="45" stroke="#000" stroke-width="1.5"/>
            <polygon points="170,45 163,41 163,49" fill="#000"/>

            <rect x="175" y="25" width="115" height="40" rx="20" fill="#337ab7" stroke="#2e6da4" stroke-width="1.5"/>
            <text x="232" y="49" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">CONFIRMED</text>

            <line x1="290" y1="45" x2="340" y2="45" stroke="#000" stroke-width="1.5"/>
            <polygon points="340,45 333,41 333,49" fill="#000"/>

            <rect x="345" y="25" width="110" height="40" rx="20" fill="#5bc0de" stroke="#46b8da" stroke-width="1.5"/>
            <text x="400" y="49" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">CHARGING</text>

            <line x1="455" y1="45" x2="505" y2="45" stroke="#000" stroke-width="1.5"/>
            <polygon points="505,45 498,41 498,49" fill="#000"/>

            <rect x="510" y="25" width="120" height="40" rx="20" fill="#5cb85c" stroke="#4cae4c" stroke-width="1.5"/>
            <text x="570" y="49" fill="#fff" font-family="Times New Roman" font-size="10" font-weight="bold" text-anchor="middle">COMPLETED</text>
        </svg>
    </div>
    <div class="figure-title">Figure 4.3: Booking and Session State Machine Flowchart</div>
</div>

<!-- ========================================== -->
<!-- CHAPTER 5: USER INTERFACE DESIGN           -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 5<br>USER INTERFACE DESIGN &amp; APPLICATION MODULES</h1>

<p>The user interface of <strong>EvChargingApp</strong> is built using <strong>Java Swing</strong> and custom visual design tokens managed by <code>AppTheme.java</code>. The interface delivers a high-contrast dark theme optimized for station command centers and depot management consoles. This chapter examines the design system and provides a detailed walkthrough of all eight functional application modules.</p>

<h2 class="section-heading">5.1 GUI Theming &amp; Design Token Architecture</h2>
<p>To eliminate the dated look and feel of standard Swing components without introducing third-party runtime dependencies, the system defines an internal design token repository (<code>AppTheme.java</code>). Table 5.1 outlines the primary tokens governing the visual layout.</p>

<div class="table-wrapper">
    <div class="table-title">Table 5.1: Color Palette &amp; Typography Tokens in AppTheme Architecture</div>
    <table>
        <thead>
            <tr>
                <th>Design Token Name</th>
                <th>Hex / RGB Value</th>
                <th>UI Application &amp; Visual Role</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><code>BG_DARK</code></td>
                <td>#121620</td>
                <td>Primary window background providing high visual depth and reduced eye strain.</td>
            </tr>
            <tr>
                <td><code>BG_CARD</code></td>
                <td>#1A2030</td>
                <td>Surface container background for responsive panels, forms, and cards.</td>
            </tr>
            <tr>
                <td><code>BG_SIDEBAR</code></td>
                <td>#0F131C</td>
                <td>Fixed navigation sidebar housing brand logo and module navigation buttons.</td>
            </tr>
            <tr>
                <td><code>ACCENT</code></td>
                <td>#00C87A</td>
                <td>Electric green accent utilized for primary call-to-action buttons, badges, and status pills.</td>
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
</div>

<h2 class="section-heading">5.2 Module-by-Module Walkthrough &amp; Screen Layouts</h2>
<p>The application architecture integrates eight responsive panels coordinated inside <code>MainFrame</code> via a central <code>CardLayout</code> container.</p>

<h3 class="sub-section-heading">5.2.1 Navigation Sidebar and Master Frame Layout</h3>
<p>The master window (<code>MainFrame.java</code>) hosts a persistent left navigation sidebar (215 px width) featuring the application brand identity, eight module selector buttons, and an active status bar. Figure 5.1 displays the structural layout schematic.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 140" width="620" height="130" xmlns="http://www.w3.org/2000/svg">
            <rect x="10" y="10" width="130" height="120" fill="#0F131C" stroke="#2A3248" stroke-width="1.5"/>
            <text x="75" y="30" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">⚡ EVCharge</text>
            <text x="75" y="44" fill="#888" font-family="Times New Roman" font-size="8" text-anchor="middle">Smart Station Platform</text>
            <line x1="20" y1="52" x2="130" y2="52" stroke="#2A3248"/>
            <text x="25" y="68" fill="#fff" font-family="Times New Roman" font-size="8">⚡ Dashboard</text>
            <text x="25" y="80" fill="#fff" font-family="Times New Roman" font-size="8">👤 Driver &amp; Vehicle</text>
            <text x="25" y="92" fill="#fff" font-family="Times New Roman" font-size="8">🔌 Charger</text>
            <text x="25" y="104" fill="#fff" font-family="Times New Roman" font-size="8">📋 Booking &amp; Queue</text>
            <text x="25" y="116" fill="#fff" font-family="Times New Roman" font-size="8">💳 Payment &amp; Review</text>

            <rect x="150" y="10" width="490" height="120" fill="#121620" stroke="#2A3248" stroke-width="1.5"/>
            <rect x="165" y="22" width="460" height="95" fill="#1A2030" stroke="#2A3248" rx="4"/>
            <text x="395" y="72" fill="#E8EAF0" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle">Dynamic CardLayout Viewport Container</text>
            <text x="395" y="88" fill="#888" font-family="Times New Roman" font-size="9" text-anchor="middle">(Switches between Dashboard, Booking, Session, Payment, Database panels)</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.1: Main Navigation Frame and Application Layout</div>
</div>

<h3 class="sub-section-heading">5.2.2 Operations Dashboard &amp; Live Telemetry Observation</h3>
<p>The <strong>Operations Dashboard</strong> (<code>DashboardPanel.java</code>) provides a consolidated overview of station operations. It features six live statistical summary cards displaying active driver, vehicle, charger, booking, session, and loyalty metrics. Positioned prominently in the center is an active session progress tracker indicating current lifecycle state (<em>PENDING &rarr; CONFIRMED &rarr; CHARGING &rarr; COMPLETED</em>). Beneath the progress tracker is the <strong>Live Telemetry Stream</strong> (<code>TelemetryChartPanel.java</code>), an autonomous graphical canvas simulating real-time station sensor telemetry including instantaneous charging power (kW), battery State of Charge, and cumulative energy delivered (kWh). Figure 5.2 illustrates this panel layout.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 140" width="620" height="130" xmlns="http://www.w3.org/2000/svg">
            <!-- Stat Cards -->
            <rect x="15" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#00C87A" stroke-width="1"/>
            <text x="62" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Active Driver</text>
            <text x="62" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">Ananya Sharma</text>

            <rect x="120" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#2A3248" stroke-width="1"/>
            <text x="167" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Active Vehicle</text>
            <text x="167" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">Tata Nexon EV</text>

            <rect x="225" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#2A3248" stroke-width="1"/>
            <text x="272" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Target Charger</text>
            <text x="272" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">GC-01 (CCS2)</text>

            <rect x="330" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#2A3248" stroke-width="1"/>
            <text x="377" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Queue Status</text>
            <text x="377" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">2 In Queue</text>

            <rect x="435" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#2A3248" stroke-width="1"/>
            <text x="482" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Session Energy</text>
            <text x="482" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">24.50 kWh</text>

            <rect x="540" y="10" width="95" height="40" rx="4" fill="#1A2030" stroke="#2A3248" stroke-width="1"/>
            <text x="587" y="26" fill="#fff" font-family="Times New Roman" font-size="9" text-anchor="middle">Loyalty Ledger</text>
            <text x="587" y="42" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">164 pts</text>

            <!-- Telemetry Graph Simulation -->
            <rect x="15" y="60" width="620" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="30" y="78" fill="#00C87A" font-family="Times New Roman" font-size="10" font-weight="bold">Live Telemetry Stream: 48.2 kW Delivery Rate | Station Voltage: 400V | SoC Curve</text>
            <polyline points="30,118 70,115 120,95 180,92 240,90 300,90 360,94 420,98 480,105 540,110 600,112" fill="none" stroke="#00C87A" stroke-width="2"/>
        </svg>
    </div>
    <div class="figure-title">Figure 5.2: Operations Dashboard with Live Telemetry Observation Panel</div>
</div>

<h3 class="sub-section-heading">5.2.3 Driver &amp; Vehicle Registration Module</h3>
<p>The <strong>Driver &amp; Vehicle Setup Panel</strong> (<code>DriverVehiclePanel.java</code>) provides side-by-side card forms. The left card captures driver name, phone number, and email. The right card captures vehicle model, battery capacity in kWh, real-time battery percentage, and physical connector inlet standard (CCS2, Type 2, CHAdeMO, GB/T). Submitting the form validates input formats and persists the records to MySQL via <code>DriverDAO</code> and <code>VehicleDAO</code>.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="290" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Driver Identity Card</text>
            <text x="35" y="44" fill="#fff" font-family="Times New Roman" font-size="8.5">Name: [ Ananya Sharma ]  Phone: [ 9876543210 ]</text>
            <text x="35" y="60" fill="#fff" font-family="Times New Roman" font-size="8.5">Email: [ ananya@example.com ]  Loyalty: 164 pts</text>

            <rect x="340" y="10" width="290" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="355" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Vehicle Specifications Card</text>
            <text x="355" y="44" fill="#fff" font-family="Times New Roman" font-size="8.5">Model: [ Tata Nexon EV ]  Capacity: [ 40.50 kWh ]</text>
            <text x="355" y="60" fill="#fff" font-family="Times New Roman" font-size="8.5">Connector: [ CCS2 &darr; ]  Battery SoC: [ 18% ]</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.3: Driver and Vehicle Registration &amp; Battery Setup Panel</div>
</div>

<h3 class="sub-section-heading">5.2.4 Charger Station &amp; Location Infrastructure Module</h3>
<p>The <strong>Charger &amp; Station Setup Panel</strong> (<code>ChargerPanel.java</code>) enables configuration of physical dispensers. It allows operators to specify charger codes, charging modes (Fast, Ultra-Fast), dispensing connector types, tariff rates per kWh, and location attributes (station name, address, verified certification, and rating). A dynamic compatibility preview badge immediately confirms whether the configured charger matches the currently active vehicle.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="280" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Charger Hardware Configuration</text>
            <text x="35" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Code: [ GC-01 ]  Mode: [ Fast &darr; ]</text>
            <text x="35" y="62" fill="#fff" font-family="Times New Roman" font-size="8.5">Connector: [ CCS2 &darr; ]  Price: [ ₹18.50/kWh ]</text>

            <rect x="330" y="10" width="300" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="345" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Charging Location &amp; Verification</text>
            <text x="345" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Location: [ GreenCharge Cafe, MG Road ]</text>
            <text x="345" y="62" fill="#00C87A" font-family="Times New Roman" font-size="8.5" font-weight="bold">&#10004; Verified Certified Station | Rating: 4.6 / 5.0</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.4: Charger Infrastructure and Location Amenities Panel</div>
</div>

<h3 class="sub-section-heading">5.2.5 Booking Dispatch &amp; Priority Queue Visualizer</h3>
<p>The <strong>Booking &amp; Priority Queue Panel</strong> (<code>BookingPanel.java</code>) is the operational hub of the platform. The left card allows creation of reservations with options for <em>NORMAL</em> or <em>EMERGENCY</em> dispatching. When submitted, the booking is validated against physical connector compatibility and charger availability before insertion into both MySQL and the in-memory <code>BookingQueue</code>. The right card displays the live queue table, visually highlighting emergency entries in distinct alert styling and sorting bookings deterministically. An operator button labeled <em>"Dispatch Next (Priority)"</em> immediately polls the highest-priority booking and transitions its status to <em>CONFIRMED</em>.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 110" width="620" height="100" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="220" height="90" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">New Booking Request</text>
            <text x="35" y="46" fill="#fff" font-family="Times New Roman" font-size="8.5">ID: [ BK-2026-01 ]</text>
            <text x="35" y="62" fill="#fff" font-family="Times New Roman" font-size="8.5">Mode: [ EMERGENCY &darr; ]</text>
            <rect x="35" y="72" width="180" height="20" rx="3" fill="#00C87A"/>
            <text x="125" y="86" fill="#121620" font-family="Times New Roman" font-size="8.5" font-weight="bold" text-anchor="middle">+ Add to Priority Queue</text>

            <rect x="260" y="10" width="370" height="90" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="275" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Live Priority Queue Table (3 Enqueued)</text>
            <text x="275" y="46" fill="#ff7675" font-family="Courier New" font-size="8" font-weight="bold">1. [EMERGENCY] BK-EMERG | Car C | 45% SoC (Leapfrogged!)</text>
            <text x="275" y="62" fill="#fff" font-family="Courier New" font-size="8">2. [NORMAL]    BK-NORM-15 | Car B | 15% SoC (Low Battery Tiebreaker)</text>
            <text x="275" y="78" fill="#888" font-family="Courier New" font-size="8">3. [NORMAL]    BK-NORM-80 | Car A | 80% SoC (Standard Position)</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.5: Priority Queue Booking Dispatch Interface</div>
</div>

<h3 class="sub-section-heading">5.2.6 Charging Session &amp; Real-Time Metering</h3>
<p>The <strong>Session Panel</strong> (<code>SessionPanel.java</code>) manages the active physical charging session. Operators or drivers enter the total kilowatt-hours delivered upon disconnecting the charger plug. Clicking <em>"Complete Session &amp; Generate Bill"</em> calculates the financial invoice using the linked charger tariff rate, sets session status to <em>COMPLETED</em>, and immediately releases the charger back to the available inventory in MySQL.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="280" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Session Dispenser Controls</text>
            <text x="35" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Energy Dispensed: [ 25.00 kWh ]</text>
            <rect x="35" y="55" width="230" height="20" rx="3" fill="#00C87A"/>
            <text x="150" y="69" fill="#121620" font-family="Times New Roman" font-size="8.5" font-weight="bold" text-anchor="middle">&#9889; Complete Session &amp; Compute Bill</text>

            <rect x="330" y="10" width="300" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="345" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Automated Bill Summary</text>
            <text x="345" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Tariff Rate: ₹18.50 / kWh  |  Status: COMPLETED</text>
            <text x="345" y="62" fill="#00C87A" font-family="Times New Roman" font-size="11" font-weight="bold">Total Amount Due: ₹462.50</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.6: Charging Session Metering &amp; Energy Delivery Panel</div>
</div>

<h3 class="sub-section-heading">5.2.7 Multi-Channel Payment Gateway &amp; Loyalty Ledger</h3>
<p>The <strong>Payment &amp; Loyalty Panel</strong> (<code>PaymentPanel.java</code>) provides financial settlement options across UPI, Credit/Debit Card, Digital Wallet, or Cash. Upon confirming payment, the transaction is logged to <code>payments</code> in MySQL with status <em>PAID</em>. Concurrently, <code>LoyaltyService</code> calculates earned reward points (\\( 1 \\text{ pt} / ₹10 \\)), automatically updates the driver's accumulated balance, and writes an audited transaction to <code>loyalty_transactions</code>.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="280" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Payment Settlement Form</text>
            <text x="35" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Payment Method: [ UPI &darr; ]</text>
            <rect x="35" y="55" width="230" height="20" rx="3" fill="#00C87A"/>
            <text x="150" y="69" fill="#121620" font-family="Times New Roman" font-size="8.5" font-weight="bold" text-anchor="middle">&#128179; Process Settlement (₹462.50)</text>

            <rect x="330" y="10" width="300" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="345" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Settlement Audit &amp; Loyalty Ledger</text>
            <text x="345" y="45" fill="#fff" font-family="Times New Roman" font-size="8.5">Payment Status: PAID &#10004; | Method: UPI</text>
            <text x="345" y="62" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Loyalty Points Earned: +46 pts | New Balance: 210 pts</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.7: Multi-Channel Payment Processing &amp; Loyalty Ledger Panel</div>
</div>

<h3 class="sub-section-heading">5.2.8 Customer Verified Feedback &amp; Review Module</h3>
<p>The <strong>Review Panel</strong> (<code>ReviewPanel.java</code>) provides an interactive 5-star rating selector and qualitative feedback text field. The submission routine triggers <code>Review.isValidReview()</code>. If the driver attempts to review a non-completed or pending reservation, an alert notification blocks submission. Once approved, the review is inserted into <code>reviews</code> in MySQL, updating the station's historical quality index.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="300" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Driver Rating &amp; Comments</text>
            <text x="35" y="45" fill="#f1c40f" font-family="Times New Roman" font-size="12">&#9733; &#9733; &#9733; &#9733; &#9733;  (5 Stars Selected)</text>
            <text x="35" y="62" fill="#fff" font-family="Times New Roman" font-size="8.5">Comment: [ "Excellent fast charging and clean amenities!" ]</text>

            <rect x="350" y="10" width="280" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="365" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Validation &amp; Status</text>
            <text x="365" y="45" fill="#00C87A" font-family="Times New Roman" font-size="8.5" font-weight="bold">&#10004; Verified Completed Booking: BK-2026-01</text>
            <text x="365" y="62" fill="#fff" font-family="Times New Roman" font-size="8.5">Station Rating Updated: 4.6 &rarr; 4.7 / 5.0</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.8: Customer Verified Star Rating and Review Interface</div>
</div>

<h3 class="sub-section-heading">5.2.9 Interactive MySQL Database Operations Console</h3>
<p>The <strong>Database Management Panel</strong> (<code>DatabasePanel.java</code>) provides station administrators with direct diagnostic controls. Operators can test live MySQL connectivity, execute diagnostic read queries against <code>drivers</code> and <code>chargers</code>, inspect raw table counts, and clear console logs in real time.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 90" width="620" height="80" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="10" width="220" height="70" rx="4" fill="#1A2030" stroke="#2A3248"/>
            <text x="35" y="28" fill="#00C87A" font-family="Times New Roman" font-size="9.5" font-weight="bold">Diagnostic Triggers</text>
            <rect x="35" y="38" width="180" height="18" rx="3" fill="#0b3c5d"/>
            <text x="125" y="51" fill="#fff" font-family="Times New Roman" font-size="8" font-weight="bold" text-anchor="middle">&#128268; Test MySQL Connection</text>
            <rect x="35" y="58" width="180" height="18" rx="3" fill="#328cc1"/>
            <text x="125" y="71" fill="#fff" font-family="Times New Roman" font-size="8" font-weight="bold" text-anchor="middle">&#128202; Refresh Database Diagnostics</text>

            <rect x="260" y="10" width="370" height="70" rx="4" fill="#0b0e14" stroke="#2A3248"/>
            <text x="270" y="26" fill="#00C87A" font-family="Courier New" font-size="8">[SQL-OK] Connected to ev_charging_app @ localhost:3306</text>
            <text x="270" y="40" fill="#fff" font-family="Courier New" font-size="8">[QUERY] Drivers count: 2 | Vehicles count: 2 | Chargers: 3</text>
            <text x="270" y="54" fill="#fff" font-family="Courier New" font-size="8">[TRANS] Payment settled ID=1: UPI ₹462.50 [SUCCESS]</text>
            <text x="270" y="68" fill="#888" font-family="Courier New" font-size="8">[AUDIT] Loyalty transaction logged: +46 pts for Driver #1</text>
        </svg>
    </div>
    <div class="figure-title">Figure 5.9: Interactive MySQL Database Diagnostic Console Panel</div>
</div>

<!-- ========================================== -->
<!-- CHAPTER 6: VERIFICATION & TESTING          -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 6<br>VERIFICATION, TESTING &amp; QUALITY ASSURANCE</h1>

<p>Quality assurance and formal verification are indispensable phases of software engineering. To ensure absolute compliance with functional specifications and prevent regressions, the <strong>EvChargingApp</strong> incorporates an automated test harness (<code>AppTestSuite.java</code>) executing unit, integration, and database connectivity test assertions. This chapter documents the verification strategy, test cases, and execution outcomes.</p>

<h2 class="section-heading">6.1 Automated Testing Strategy &amp; Harness</h2>
<p>The testing framework is architected as an automated test harness capable of execution from IDE runners, command-line scripts, or continuous build pipelines. The test suite evaluates six critical operational subsystems:</p>
<ol>
    <li><strong>Priority Queue Dispatching:</strong> Verifies that <em>EMERGENCY</em> requests jump to the head of the queue regardless of submission order, and that non-emergency bookings are sorted strictly by ascending State of Charge (SoC).</li>
    <li><strong>Connector Compatibility:</strong> Asserts that matching connector standards evaluate to <code>true</code>, incompatible standards evaluate to <code>false</code>, and case sensitivity differences are normalized.</li>
    <li><strong>Loyalty Point Accrual:</strong> Validates exact integer point allocation (1 pt per ₹10 paid) and guarantees that unpaid transactions award 0 points.</li>
    <li><strong>Lifecycle State Machine:</strong> Confirms sequential state transitions (<em>PENDING &rarr; CONFIRMED &rarr; CHARGING &rarr; COMPLETED</em>).</li>
    <li><strong>Review Validation Rules:</strong> Asserts that reviews on pending bookings and invalid star ratings (e.g., 0 or 6) are strictly rejected.</li>
    <li><strong>Live Database Connectivity:</strong> Establishes a live JDBC connection to MySQL and asserts query execution across key tables.</li>
</ol>

<h2 class="section-heading">6.2 Test Matrix &amp; Execution Results (21 Test Assertions)</h2>
<p>Table 6.1 details the complete verification matrix comprising all 21 deterministic test assertions implemented in <code>AppTestSuite.java</code>.</p>

<div class="table-wrapper">
    <div class="table-title">Table 6.1: Automated Test Suite Verification Matrix (21 Assertions)</div>
    <table>
        <thead>
            <tr>
                <th>Test ID</th>
                <th>Subsystem / Target Feature</th>
                <th>Test Scenario Description</th>
                <th>Expected Result</th>
                <th>Actual Result</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>TC-01</strong></td>
                <td>Priority Queue</td>
                <td>Enqueue 3 bookings (Normal-80%, Normal-15%, Emergency-45%)</td>
                <td>Queue size == 3</td>
                <td>Queue size == 3</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-02</strong></td>
                <td>Priority Queue</td>
                <td>First poll from queue containing emergency booking</td>
                <td>Dispatched == "BK-EMERG"</td>
                <td>Dispatched == "BK-EMERG"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-03</strong></td>
                <td>Priority Queue</td>
                <td>Second poll between 15% SoC and 80% SoC vehicles</td>
                <td>Dispatched == "BK-NORM-15"</td>
                <td>Dispatched == "BK-NORM-15"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-04</strong></td>
                <td>Priority Queue</td>
                <td>Third poll for remaining vehicle in queue</td>
                <td>Dispatched == "BK-NORM-80"</td>
                <td>Dispatched == "BK-NORM-80"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-05</strong></td>
                <td>Connector Engine</td>
                <td>CCS2 Vehicle mating with CCS2 Charger</td>
                <td>Compatibility == TRUE</td>
                <td>Compatibility == TRUE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-06</strong></td>
                <td>Connector Engine</td>
                <td>CCS2 Vehicle mating with Type 2 Charger</td>
                <td>Compatibility == FALSE</td>
                <td>Compatibility == FALSE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-07</strong></td>
                <td>Connector Engine</td>
                <td>Type 2 Vehicle with lowercase string "type 2"</td>
                <td>Compatibility == TRUE</td>
                <td>Compatibility == TRUE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-08</strong></td>
                <td>Loyalty Service</td>
                <td>Completed payment of ₹462.50 awards floor(462.50 / 10)</td>
                <td>Awarded points == 46</td>
                <td>Awarded points == 46</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-09</strong></td>
                <td>Loyalty Service</td>
                <td>Driver cumulative loyalty balance incremented</td>
                <td>Balance == Initial + 46</td>
                <td>Balance == Initial + 46</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-10</strong></td>
                <td>Loyalty Service</td>
                <td>Unpaid payment transaction submitted for rewards</td>
                <td>Awarded points == 0</td>
                <td>Awarded points == 0</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-11</strong></td>
                <td>Lifecycle State</td>
                <td>Initial booking instantiation state check</td>
                <td>Status == "PENDING"</td>
                <td>Status == "PENDING"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-12</strong></td>
                <td>Lifecycle State</td>
                <td>Booking confirmation transition check</td>
                <td>Status == "CONFIRMED"</td>
                <td>Status == "CONFIRMED"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-13</strong></td>
                <td>Lifecycle State</td>
                <td>Start charging transition check</td>
                <td>Status == "CHARGING"</td>
                <td>Status == "CHARGING"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-14</strong></td>
                <td>Lifecycle State</td>
                <td>Complete booking transition check</td>
                <td>Status == "COMPLETED"</td>
                <td>Status == "COMPLETED"</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-15</strong></td>
                <td>Review Rules</td>
                <td>Review submitted on PENDING booking</td>
                <td>isValidReview == FALSE</td>
                <td>isValidReview == FALSE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-16</strong></td>
                <td>Review Rules</td>
                <td>Review submitted on COMPLETED booking (5 Stars)</td>
                <td>isValidReview == TRUE</td>
                <td>isValidReview == TRUE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-17</strong></td>
                <td>Review Rules</td>
                <td>Review submitted with out-of-bounds rating: 6 Stars</td>
                <td>isValidReview == FALSE</td>
                <td>isValidReview == FALSE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-18</strong></td>
                <td>Review Rules</td>
                <td>Review submitted with out-of-bounds rating: 0 Stars</td>
                <td>isValidReview == FALSE</td>
                <td>isValidReview == FALSE</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-19</strong></td>
                <td>Live Database</td>
                <td>Active connection acquisition via DBConnection</td>
                <td>Connection != null &amp;&amp; !isClosed()</td>
                <td>Connection active</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-20</strong></td>
                <td>Live Database</td>
                <td>Query execution: SELECT COUNT(*) FROM drivers</td>
                <td>Count returned &gt; 0</td>
                <td>Count returned &gt; 0</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
            <tr>
                <td><strong>TC-21</strong></td>
                <td>Live Database</td>
                <td>Query execution: SELECT COUNT(*) FROM chargers</td>
                <td>Count returned &gt; 0</td>
                <td>Count returned &gt; 0</td>
                <td><strong style="color: green;">PASS</strong></td>
            </tr>
        </tbody>
    </table>
</div>

<p>As confirmed in Table 6.1, all 21 assertions pass unconditionally with zero failures, demonstrating the structural reliability and deterministic precision of the system.</p>

<div class="figure-wrapper">
    <div class="figure-box">
        <svg class="diagram-svg" viewBox="0 0 650 70" width="600" height="60" xmlns="http://www.w3.org/2000/svg">
            <rect x="20" y="15" width="610" height="36" rx="4" fill="#00C87A" stroke="#009955" stroke-width="1.5"/>
            <text x="325" y="38" fill="#121620" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle">&#10004; AUTOMATED TEST HARNESS EXECUTION SUMMARY: 21 PASSED, 0 FAILED (100% SUCCESS)</text>
        </svg>
    </div>
    <div class="figure-title">Figure 6.1: Automated Test Suite (AppTestSuite) Execution Flow</div>
</div>

<!-- ========================================== -->
<!-- CHAPTER 7: CONCLUSION & FUTURE SCOPE       -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 7<br>CONCLUSION &amp; FUTURE SCOPE</h1>

<p>The successful development and formal verification of the <strong>EV Charging Management System (EvChargingApp)</strong> establishes a robust, highly functional, and architecturally clean platform addressing real-world electric mobility dispatching challenges. This chapter synthesizes the core deliverables, details the pedagogical insights attained in object-oriented engineering, identifies system limitations, and maps an industrial enhancement roadmap.</p>

<h2 class="section-heading">7.1 Summary of Deliverables &amp; Achievements</h2>
<p>The project successfully satisfied all established requirements through the following technical deliverables:</p>
<ul>
    <li><strong>Multi-Tiered Architectural Integrity:</strong> Full decoupling between Java Swing presentation panels, business logic services, data access objects, and the persistent MySQL database.</li>
    <li><strong>Deterministic Priority Scheduling:</strong> Formulation and verification of an algorithmic priority queue that prioritizes emergency fleets while resolving standard queue positions through battery depletion metrics.</li>
    <li><strong>Automated Hardware Compatibility Guarding:</strong> Elimination of driver-side reservation errors by enforcing physical connector compatibility prior to booking persistence.</li>
    <li><strong>Comprehensive Accounting &amp; Loyalty Engine:</strong> Automated session energy calculation, multi-method payment auditing, and transparent loyalty point accrual and redemption.</li>
    <li><strong>Strict Feedback Integrity:</strong> A state-machine-governed customer review system permitting ratings strictly on verified, completed sessions.</li>
    <li><strong>Empirical Verification:</strong> Implementation of an automated test suite verifying 21 distinct system assertions with 100% pass rates.</li>
</ul>

<h2 class="section-heading">7.2 Pedagogical Outcomes in Object-Oriented Programming</h2>
<p>From an educational perspective within the curriculum of <strong>PBCST304 Object Oriented Programming</strong>, the project delivered extensive practical mastery across essential software paradigms:</p>
<ol>
    <li><strong>Encapsulation in Practice:</strong> Moving beyond theoretical accessor definitions to enforce inviolable domain invariants (e.g., battery ranges, non-negative points) directly within model mutators.</li>
    <li><strong>Dynamic Polymorphism &amp; Lambdas:</strong> Utilizing Java functional interfaces and custom comparators to dynamically alter collection ordering algorithms at runtime.</li>
    <li><strong>Architectural Decoupling via DAO:</strong> Appreciating the necessity of isolating database connection lifecycles and SQL syntax from user interface event dispatch threads (EDT).</li>
    <li><strong>State Machine Modeling:</strong> Engineering lifecycle transitions as explicit state machine progressions to prevent illegal operations.</li>
</ol>

<h2 class="section-heading">7.3 Limitations &amp; Future Enhancements</h2>
<p>While the current prototype achieves its academic and operational goals, future iterations could expand its capabilities into industrial-scale environments:</p>
<ul>
    <li><strong>Open Charge Point Protocol (OCPP 2.0.1) Integration:</strong> Upgrading the simulated telemetry engine to connect with physical EVSE hardware chargers via WebSocket-based OCPP message frames.</li>
    <li><strong>Dynamic Tariff &amp; Grid Peak Shaving:</strong> Incorporating real-time electricity grid pricing feeds to adjust station charging rates dynamically during peak grid stress.</li>
    <li><strong>Cross-Platform Mobile Application:</strong> Developing complementary Android and iOS client applications communicating via RESTful or gRPC APIs to allow drivers to track queue status remotely.</li>
    <li><strong>Predictive Machine Learning for Arrival Times:</strong> Implementing predictive regression models to forecast station wait times based on historical session durations and traffic conditions.</li>
</ul>

<!-- ========================================== -->
<!-- CHAPTER 8: REFERENCES                      -->
<!-- ========================================== -->
<div class="page-break"></div>
<h1 class="chapter-title">CHAPTER 8<br>REFERENCES &amp; BIBLIOGRAPHY</h1>

<ol style="padding-left: 20pt; line-height: 1.8;">
    <li>Oracle Corporation. <em>Java&reg; Platform, Standard Edition &amp; Java Development Kit Version 21 API Specification</em>. Oracle Technology Network, 2023. Available: <span style="font-family: Courier New; font-size: 10pt;">https://docs.oracle.com/en/java/javase/21/</span></li>
    <li>E. Gamma, R. Helm, R. Johnson, and J. Vlissides. <em>Design Patterns: Elements of Reusable Object-Oriented Software</em>. Addison-Wesley Professional, 1994.</li>
    <li>J. Bloch. <em>Effective Java</em>, 3rd ed. Boston, MA: Addison-Wesley, 2018.</li>
    <li>MySQL AB / Oracle Corporation. <em>MySQL Connector/J 9.1 Developer Guide and API Documentation</em>. 2024. Available: <span style="font-family: Courier New; font-size: 10pt;">https://dev.mysql.com/doc/connector-j/en/</span></li>
    <li>Y. Cao, N. Wang, and T. Jiang. "Intelligent Priority Scheduling of Electric Vehicle Charging Stations Using Real-Time State of Charge Metrics," <em>IEEE Transactions on Smart Grid</em>, vol. 12, no. 4, pp. 3120–3131, July 2021.</li>
    <li>X. Tan, Y. Sun, and D. H. K. Tsang. "Queueing Analysis and Optimal Dispatch for Electric Vehicle Charging Stations with Renewable Generation," <em>IEEE Transactions on Intelligent Transportation Systems</em>, vol. 23, no. 3, pp. 2450–2462, March 2022.</li>
    <li>International Electrotechnical Commission (IEC). <em>IEC 62196-1: Plugs, socket-outlets, vehicle connectors and vehicle inlets - Conductive charging of electric vehicles</em>. Geneva: IEC, 2022.</li>
    <li>Open Charge Alliance. <em>Open Charge Point Protocol (OCPP) Version 2.0.1 Specification</em>. Arnhem, Netherlands: OCA, 2020.</li>
    <li>C. J. Date. <em>An Introduction to Database Systems</em>, 8th ed. Boston: Pearson Education, 2004.</li>
    <li>B. Eckel. <em>Thinking in Java</em>, 4th ed. Upper Saddle River, NJ: Prentice Hall, 2006.</li>
</ol>

</body>
</html>
`;

const htmlFilePath = path.join(__dirname, 'EV_Charging_App_Project_Report.html');
fs.writeFileSync(htmlFilePath, htmlContent, 'utf8');
console.log("HTML report generated successfully at: " + htmlFilePath);

const pdfFilePath = path.join(__dirname, 'EV_Charging_App_Project_Report.pdf');
const edgePath = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';

console.log("Compiling PDF via Microsoft Edge headless print-to-pdf...");
try {
    const cmd = `"${edgePath}" --headless --disable-gpu --print-to-pdf="${pdfFilePath}" --no-pdf-header-footer "${htmlFilePath}"`;
    execSync(cmd, { stdio: 'inherit' });
    console.log("PDF generated successfully at: " + pdfFilePath);
} catch (err) {
    console.error("Error generating PDF:", err);
}
