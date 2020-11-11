WITH cte AS (
    SELECT 
        IDItems, 
	IDtags,
        ROW_NUMBER() OVER (
            PARTITION BY 
               IDItems, 
		IDtags
            ORDER BY 
               IDItems, 
	       IDtags
        ) row_num
     FROM 
        ItemsTags
)
DELETE FROM cte
WHERE row_num > 1;