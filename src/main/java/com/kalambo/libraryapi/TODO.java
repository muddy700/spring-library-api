package com.kalambo.libraryapi;

// TODO: All TODOs should be recorded here to avoid repetitions.

/*
 * 1. In all repositories, create a reusable function named => findByQuery,
 * which will pass all querying params as optional and implemented using @Query
 * or DB Function/Procedure ==> Then remove all other individual findByFunctions
 * in all repositories, also do it for getAll(pageable) functions
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
 * 8. Delete all relational data in delete function of all
 * service-implementations
 * 
 * 11. Add index on createdAt field for some entities iff applicable
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
 * 4. Only users with Student role can add a review.
 * 5. Students can only update their own profiles.
 * 6. Accounts for Students are created by staff at the library
 * 7. Users are required to change their passwords after each 30 days
 */