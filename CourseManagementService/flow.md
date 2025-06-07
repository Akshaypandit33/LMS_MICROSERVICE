# Complete Data Flow and Entity Relationship Explanation

## 1. Entity Hierarchy and Relationships

### Core Entity Structure
```
BaseEntity (Abstract)
├── tenant_id, created_at, updated_at, is_active
├── created_by, updated_by
└── Common audit fields for all entities

Subject (Master Template)
├── Inherits from BaseEntity
├── Contains: subject_name, description, type, credits, hours
└── Represents generic subject definition

BranchSubject (Branch-Specific Implementation)  
├── Inherits from BaseEntity
├── Links to: Subject (Many-to-One)
├── Contains: branch_id, subject_code, semester, academic_year
└── Branch-specific subject configuration

FacultySubjectAssignment (Teaching Assignment)
├── Inherits from BaseEntity  
├── Links to: BranchSubject (Many-to-One)
├── Contains: faculty_id, assignment_type, academic_year
└── Who teaches what, where, and when
```

## 2. Data Flow Step-by-Step

### Step 1: Master Subject Creation
```
Admin creates "Database Management Systems" subject:
┌─────────────────────────────────────────┐
│ Subject Entity                          │
├─────────────────────────────────────────┤
│ id: 1                                   │
│ tenant_id: "university_abc"             │
│ subject_name: "Database Management"     │
│ subject_type: THEORY_PRACTICAL          │
│ credits: 4                              │
│ total_hours: 60                         │
│ theory_hours: 40                        │
│ practical_hours: 20                     │
└─────────────────────────────────────────┘

This creates a REUSABLE template that can be assigned to multiple branches
```

### Step 2: Branch-Specific Subject Assignment
```
Same subject assigned to different branches with different codes:

AIML Branch Assignment:
┌─────────────────────────────────────────┐
│ BranchSubject Entity                    │
├─────────────────────────────────────────┤
│ id: 101                                 │
│ tenant_id: "university_abc"             │
│ subject_id: 1 (FK to Subject)           │
│ branch_id: 10 (AIML Branch)             │
│ subject_code: "db1"                     │
│ semester: 3                             │
│ academic_year: "2024-25"                │
│ is_mandatory: true                      │
└─────────────────────────────────────────┘

Data Science Branch Assignment:
┌─────────────────────────────────────────┐
│ BranchSubject Entity                    │
├─────────────────────────────────────────┤
│ id: 102                                 │
│ tenant_id: "university_abc"             │
│ subject_id: 1 (Same Subject!)           │
│ branch_id: 11 (DS Branch)               │
│ subject_code: "db009"                   │
│ semester: 2                             │
│ academic_year: "2024-25"                │
│ is_mandatory: true                      │
└─────────────────────────────────────────┘
```

### Step 3: Faculty Assignment to Branch Subjects
```
Different faculty assigned to teach the same subject in different branches:

Faculty Assignment for AIML Branch:
┌─────────────────────────────────────────┐
│ FacultySubjectAssignment Entity         │
├─────────────────────────────────────────┤
│ id: 201                                 │
│ tenant_id: "university_abc"             │
│ branch_subject_id: 101 (AIML db1)       │
│ faculty_id: 501 (Dr. Smith)             │
│ assignment_type: PRIMARY_INSTRUCTOR     │
│ academic_year: "2024-25"                │
│ assigned_hours: 4                       │
│ is_coordinator: true                    │
└─────────────────────────────────────────┘

Faculty Assignment for DS Branch:
┌─────────────────────────────────────────┐
│ FacultySubjectAssignment Entity         │
├─────────────────────────────────────────┤
│ id: 202                                 │
│ tenant_id: "university_abc"             │
│ branch_subject_id: 102 (DS db009)       │
│ faculty_id: 502 (Dr. Johnson)           │
│ assignment_type: PRIMARY_INSTRUCTOR     │
│ academic_year: "2024-25"                │
│ assigned_hours: 4                       │
│ is_coordinator: false                   │
└─────────────────────────────────────────┘
```

## 3. Complete Data Flow Scenarios

### Scenario A: Creating a New Subject for Multiple Branches

```
Step 1: Create Master Subject
INPUT: SubjectDTO {
    name: "Machine Learning",
    type: THEORY_PRACTICAL,
    credits: 3,
    theory_hours: 30,
    practical_hours: 30
}

PROCESS: SubjectService.createSubject()
OUTPUT: Subject entity with ID=2

Step 2: Assign to AIML Branch (Semester 5)
INPUT: BranchSubjectDTO {
    subject_id: 2,
    branch_id: 10,
    subject_code: "ml101",
    semester: 5
}

PROCESS: BranchSubjectService.assignSubjectToBranch()
VALIDATION: Check if subject_code "ml101" is unique in branch 10
OUTPUT: BranchSubject entity with ID=103

Step 3: Assign to DS Branch (Semester 4)
INPUT: BranchSubjectDTO {
    subject_id: 2,
    branch_id: 11,
    subject_code: "ml501",
    semester: 4
}

PROCESS: BranchSubjectService.assignSubjectToBranch()
VALIDATION: Check if subject_code "ml501" is unique in branch 11
OUTPUT: BranchSubject entity with ID=104

Step 4: Assign Faculty to AIML ML Course
INPUT: FacultyAssignmentDTO {
    branch_subject_id: 103,
    faculty_id: 503,
    assignment_type: PRIMARY_INSTRUCTOR
}

PROCESS: FacultySubjectAssignmentService.assignFaculty()
VALIDATION: Check faculty availability and conflicts
OUTPUT: FacultySubjectAssignment entity with ID=203
```

### Scenario B: Query Operations Data Flow

```
Query: "Get all subjects for AIML Branch, Semester 3"

Step 1: BranchSubjectRepository Query
SQL: SELECT bs.*, s.subject_name, s.credits 
     FROM branch_subjects bs 
     JOIN subjects s ON bs.subject_id = s.id 
     WHERE bs.tenant_id = 'university_abc' 
     AND bs.branch_id = 10 
     AND bs.semester = 3

Step 2: Data Retrieved
┌──────────────────────────────────────────────────────┐
│ BranchSubject + Subject Data                         │
├──────────────────────────────────────────────────────┤
│ subject_code: "db1"                                  │
│ subject_name: "Database Management Systems"         │
│ credits: 4                                           │
│ semester: 3                                          │
│ branch_id: 10                                        │
└──────────────────────────────────────────────────────┘

Step 3: Get Faculty Information (if needed)
Query: Find all faculty teaching these subjects
SQL: SELECT fsa.*, f.faculty_name 
     FROM faculty_subject_assignments fsa
     WHERE fsa.branch_subject_id IN (101, ...)

Step 4: Response Assembled
OUTPUT: List of SubjectWithFacultyDTO containing complete information
```

## 4. Entity Relationship Detailed Flow

### One-to-Many Relationships Flow
```
Subject (1) ──────► BranchSubject (Many)
   │                      │
   │                      ▼
   │               FacultySubjectAssignment (Many)
   │                      │
   └──────────────────────┘
   
Data Flow:
1. One Subject can be assigned to multiple branches
2. Each BranchSubject can have multiple faculty assignments
3. Each faculty assignment links back to the original subject through BranchSubject
```

### Multi-Tenant Data Isolation Flow
```
Tenant Request: university_abc wants to see all subjects

Step 1: Tenant Filter Applied
WHERE tenant_id = 'university_abc'

Step 2: Data Retrieved
┌─ Tenant: university_abc ─┐
│ Subject 1: Database      │
│ Subject 2: ML            │
│ Subject 3: AI            │
└─────────────────────────┘

Step 3: Branch-Specific Data
┌─ Branch 10 (AIML) ──────┐
│ db1 -> Database         │
│ ml101 -> ML             │
└────────────────────────┘

┌─ Branch 11 (DS) ────────┐
│ db009 -> Database       │
│ ml501 -> ML             │
└────────────────────────┘

Different tenant (university_xyz) cannot see this data due to tenant_id filter
```

## 5. Complex Query Data Flow Examples

### Example 1: Faculty Workload Report
```
Query: "Show all subjects taught by Dr. Smith in 2024-25"

Data Flow:
FacultySubjectAssignment 
├── WHERE faculty_id = 501 AND academic_year = '2024-25'
├── JOIN BranchSubject ON branch_subject_id
├── JOIN Subject ON subject_id
└── Result: Complete faculty teaching load

Output Structure:
┌────────────────────────────────────────┐
│ Faculty: Dr. Smith (ID: 501)           │
├────────────────────────────────────────┤
│ Branch: AIML | Subject: db1 | Hours: 4 │
│ Branch: AIML | Subject: ai1 | Hours: 3 │
│ Total Hours: 7                         │
└────────────────────────────────────────┘
```

### Example 2: Branch Curriculum View
```
Query: "Show complete curriculum for AIML Branch"

Data Flow:
BranchSubject 
├── WHERE branch_id = 10 (AIML)
├── JOIN Subject for subject details
├── LEFT JOIN FacultySubjectAssignment for faculty info
└── ORDER BY semester, subject_code

Output Structure:
┌─ AIML Branch Curriculum ───────────────┐
│ Semester 1:                            │
│   └── math1: Mathematics (Dr. A)       │
│ Semester 2:                            │
│   └── prog1: Programming (Dr. B)       │
│ Semester 3:                            │
│   └── db1: Database (Dr. Smith)        │
└────────────────────────────────────────┘
```

## 6. Data Consistency and Validation Flow

### Subject Code Uniqueness Validation
```
Before creating BranchSubject:

Step 1: Check Existing Codes
Query: SELECT COUNT(*) FROM branch_subjects 
       WHERE tenant_id = ? AND branch_id = ? AND subject_code = ?

Step 2: Validation Logic
IF count > 0:
    THROW Exception("Subject code already exists in this branch")
ELSE:
    PROCEED with creation

Step 3: Create with Unique Constraint
Database enforces uniqueness at DB level as backup
```

### Faculty Assignment Validation
```
Before assigning faculty:

Step 1: Check Faculty Existence
API Call: GET /academic-structure/faculty/{facultyId}
IF not exists: THROW Exception

Step 2: Check Time Conflicts  
Query existing assignments for same academic year
Validate no scheduling conflicts

Step 3: Check Branch Association
Ensure faculty belongs to department that owns the branch

Step 4: Create Assignment
Only if all validations pass
```

This data flow ensures:
- ✅ **Data Integrity**: All relationships are maintained
- ✅ **Multi-tenant Isolation**: Complete data separation
- ✅ **Flexible Subject Coding**: Same subject, different codes per branch
- ✅ **Scalable Faculty Management**: Multiple assignments handled efficiently
- ✅ **Performance**: Optimized queries with proper indexing
- ✅ **Consistency**: Validation at multiple levels