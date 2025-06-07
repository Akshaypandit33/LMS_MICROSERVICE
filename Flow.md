flow of the program

1. create user
2. create college and then assign userid


// Course Management Functions
public Course createCourse(CreateCohelp with developing complete LMS with following service and their functions in depth and the database table eachh service will have and the service dependency
Service Separation Strategy:
8 Core Services with distinct responsibilities:
1. User Management - Authentication & authorization
2. College Management - Tenant provisioning
3. Academic Structure - Hierarchy & faculty assignments
4. Course Management - Course catalog & offerings
5. Student Enrollment - Admissions & registrations
6. Assessment & Grading - Assignments & grades
7. Attendance Management - Attendance tracking
8. Student Dashboard - Data aggregation service

using spring boot think yourself as a senior software developerurseRequest request);
public Course getCourseById(Long courseId);
public Course getCourseByCode(String courseCode, String tenantId);
public List<Course> getAllCourses(String tenantId);
public Course updateCourse(Long courseId, UpdateCourseRequest request);
public void activateCourse(Long courseId);
public void deactivateCourse(Long courseId);
public List<Course> searchCourses(CourseSearchCriteria criteria);

// Course Branch Mapping Functions
public CourseBranchMapping mapCourseToBranch(Long courseId, Long branchId, Integer semester, boolean mandatory);
public List<CourseBranchMapping> getCoursesByBranch(Long branchId, Integer semester);
public List<CourseBranchMapping> getBranchesByCourse(Long courseId);
public void updateCourseBranchMapping(Long mappingId, UpdateMappingRequest request);
public void removeCourseBranchMapping(Long mappingId);

// Prerequisite Management Functions
public CoursePrerequisite addPrerequisite(Long courseId, Long prerequisiteCourseId, boolean mandatory);
public List<CoursePrerequisite> getCoursePrerequisites(Long courseId);
public List<Course> getPrerequisiteChain(Long courseId);
public void removePrerequisite(Long prerequisiteId);
public boolean checkPrerequisites(Long studentUserId, Long courseId);

// Course Offering Functions
public CourseOffering createCourseOffering(CreateOfferingRequest request);
public List<CourseOffering> getCourseOfferings(String academicYear, Integer semester, Long branchId);
public CourseOffering getCourseOfferingById(Long offeringId);
public CourseOffering updateCourseOffering(Long offeringId, UpdateOfferingRequest request);
public void updateOfferingStatus(Long offeringId, OfferingStatus status);
public void assignFacultyToOffering(Long offeringId, Long facultyUserId);
public void addCoInstructor(Long offeringId, Long facultyUserId);
public void updateEnrollmentCount(Long offeringId, Integer enrolledCount);

// Schedule Management Functions
public CourseSchedule addSchedule(Long offeringId, CreateScheduleRequest request);
public List<CourseSchedule> getCourseSchedule(Long offeringId);
public List<CourseSchedule> getFacultySchedule(Long facultyUserId, String academicYear, Integer semester);
public CourseSchedule updateSchedule(Long scheduleId, UpdateScheduleRequest request);
public void deleteSchedule(Long scheduleId);
public boolean checkScheduleConflict(CreateScheduleRequest request);

// Course Material Functions
public CourseMaterial addCourseMaterial(Long offeringId, CreateMaterialRequest request);
public List<CourseMaterial> getCourseMaterials(Long offeringId);
public CourseMaterial updateCourseMaterial(Long materialId, UpdateMaterialRequest request);
public void deleteCourseMaterial(Long materialId);

// Course Unit Functions
public CourseUnit addCourseUnit(Long courseId, CreateUnitRequest request);
public List<CourseUnit> getCourseUnits(Long courseId);
public CourseUnit updateCourseUnit(Long unitId, UpdateUnitRequest request);
public void deleteCourseUnit(Long unitId);
public void reorderCourseUnits(Long courseId, List<Long> unitIds);

