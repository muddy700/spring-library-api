// TODO: All TODOs should be recorded here to avoid repetitions.

/*
 * 1. Use 'private' for all Autowired variables in all classes
 * 
 * 2. Add actor email in the logs ie.. GET - /api/v1/users/ by user1@gmail.com
 * 
 * 3. Add controller and service for generating system reports within a certain
 * date range eg..
 * - Books added/removed
 * - Students registered
 * - Students entered in the system and / or only once or repetitive records
 * - Most viewed book(Book that has been read with many Students).
 * - Read record for a single book
 * NB: Add comparison with the previous month of the date-range specified if
 * possible
 * 
 * 4. Validate password with pattern ==>
 * https://www.bezkoder.com/spring-boot-validate-request-body/
 * 
 * 5. Check for duplications for POST and PUT requests in all service-impl as
 * done in userServiceImpl before saving the records.
 * 
 * 6. Add implementations for all applicable events-handlers
 * 
 * 7. Add Mini versions of all response classes ie.. IRole extends IBaseRolefor
 * optimization
 */

// ! Warnings
/*
 * In Seeders and other classes other than controllers and services...
 * 1. Use repositories only for 'Read' operations and
 * Services for other operations(Create, Update and Delete)
 * 2.
 */