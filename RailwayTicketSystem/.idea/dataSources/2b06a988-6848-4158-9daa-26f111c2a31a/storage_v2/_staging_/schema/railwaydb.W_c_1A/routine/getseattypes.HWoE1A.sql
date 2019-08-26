create procedure getSeatTypes(IN trainId int)
  begin
declare v_counter int unsigned default 0;
while v_counter < 4 do
	SELECT carriage_types.name, CASE 
            WHEN carriage_types.name = 'Coupe' THEN trains.coupe 
             WHEN carriage_types.name = 'Reserved Seat' THEN trains.reserved_seat 
             ELSE trains.common END AS free_seats, CASE 
             WHEN carriage_types.name = 'Coupe' THEN trains.coupe_price 
             WHEN carriage_types.name = 'Reserved Seat' THEN trains.reserved_price 
             ELSE trains.common_price END AS price, trains.id AS train_id FROM trains 
             JOIN carriage_types ON carriage_types.id = v_counter WHERE trains.id = trainId;
    set v_counter = v_counter + 1;
    end while;
    commit;
    end;

call getSeatTypes(1);

