SELECT B.name,BC.name
FROM books B
         INNER JOIN book_categories BC
                    ON B.book_category_id=BC.id;

-- US01 -1
    select  count(id) from users; -- 4891

    select count(distinct id) from users; -- 4891

-- US01-2
select * from users;

-- US02-1
select count(*) from book_borrow where is_returned=0;

-- US03-1
select name from book_categories;

-- US04-1
select b.name, b.author, b.year, b.isbn, c.name, b.description  from books b join book_categories c on c.id=b.book_category_id
         where b.name = 'Clean Code' and
               author = 'Lionel Messi' and
               isbn = 999239923 and
               year = 2023 and
               c.name = 'Fantasy' and
               b.description = 'Test run for ares AK.';

