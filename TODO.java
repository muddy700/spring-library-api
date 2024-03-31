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
 * 6. Add implementations for all applicable events-handlers
 * 
 * 7. Add base classes ie.. BaseResponse(for getAll vs getById)
 * 
 * 8. Delete all relational data in delete function of all
 * service-implementations
 * 
 * 9. Optimize queries ie.. eager/lazy loading, minimize relationships entities
 * responses ie IUserV2 with few fields
 * 
 * 11. See if there's a need to add index on createdAt field
 * 
 * 12. Add data flow diagrams ie.. controller -> Service(Dto) ->
 * Controller(IResponse)
 * 
 * 13. See if there's a need to store IError in the database with traceId
 * traceId: TID-2023-0329-10015, and send email to admin
 */

// ! Warnings
/*
 * In Seeders and other classes other than controllers and services...
 * 
 * 1. Use repositories only for 'Read' operations and
 * Services for other operations(Create, Update and Delete)
 * 
 * 2.
 */

// * Assumptions
/*
 * System assumptions
 * 1. One author cannot have multiple books with the same name.
 * 2. One user can only add one review for each single book.
 * 3. One user can only have one role.
 */