create database machti_ImportExport


use machti_ImportExport

CREATE TABLE produit (
    refProduit INT PRIMARY KEY,
    libelleProduit NVARCHAR(100),
    description NVARCHAR(255),
    typeProduit NVARCHAR(50) check (typeProduit in ('Produit agricole', 'Machine')),
    prix FLOAT,
    poidsKG FLOAT,
    dimensions NVARCHAR(100)
);

insert into produit values (1,'Pommes','Pommes agricole','Produit agricole',150,20,'10x10x10'),
						   (2,'Tracteur','Machine','Machine',14000,1500,'300x200x200'),
						   (3,'Oranges','Norriture','Produit agricole',170,20,'10x10x10')



CREATE TABLE tariffExport (
    idTariff INT PRIMARY KEY IDENTITY,
    pays_Destination NVARCHAR(30) CHECK (pays_Destination IN ('Espagne', 'France', 'Belgique', 'Allemagne', 'Royaume-uni', 'Tunisie')),
    refPr INT,
    tariff_rate FLOAT,
    CONSTRAINT fk_tariffAgriculturalExport_Produit FOREIGN KEY (refPr) REFERENCES produit(refProduit)
);

CREATE TABLE tariffImport (
    idTariff INT PRIMARY KEY IDENTITY,
    pays_Origine NVARCHAR(30) CHECK (pays_Origine IN ('Espagne', 'France', 'Belgique', 'Allemagne', 'Royaume-uni', 'Tunisie')),
    refPr INT,
    tariff_rate FLOAT,
    CONSTRAINT fk_tariffAgriculturalImport_Produit FOREIGN KEY (refPr) REFERENCES produit(refProduit)
);


------------------------------------------TriggerExport-------------------------------------------------
create trigger TGR_tariffRateImport on tariffImport after insert, update
as 
begin
	declare @typePr nvarchar(50);
	declare @refPro int ;
	select @refPro = refpr from inserted ;
	select @typePr = typeProduit from produit where refProduit = @refPro ;

	if @typePr = 'Produit agricole'
		begin
			update tariffImport 
			set tariff_rate = 0.025
			where refPr = @refPro
		end
	else 
		begin
			update tariffImport
			set tariff_rate = 0.10
			where refPr = @refPro
		end
end



drop trigger TGR_tariffRateImport


------------------------------------------TriggerExport_PrAGRICOLE-------------------------------------------------
create trigger TGR_tariffrateExport_ProduitAgricole on tariffExport after insert 
as
begin
	
	declare @typePr nvarchar(50);
	declare @refPr int ;
	declare @paysDestination nvarchar(30);


	select @refPr = refPr from inserted ;
	select @typePr = typeproduit from produit where refProduit = @refPr ;
	select @paysDestination = pays_Destination from inserted ;


	if @typePr = 'Produit agricole'
		begin
			if @paysDestination = 'France'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Espagne'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Belgique'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Allemagne'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Royaume-uni'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else 
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
		end
end

drop trigger TGR_tariffrateExport_ProduitAgricole


------------------------------------------TriggerExport_MACHINE-------------------------------------------------


create trigger TGR_tariffrateExport_Machine on tariffExport after insert 
as
begin
	
	declare @typePr nvarchar(50);
	declare @refPr int ;
	declare @paysDestination nvarchar(30);


	select @refPr = refPr from inserted ;
	select @typePr = typeproduit from produit where refProduit = @refPr ;
	select @paysDestination = pays_Destination from inserted ;


	if @typePr = 'Machine'
		begin
			if @paysDestination = 'France'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Espagne'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Belgique'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Allemagne'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else if @paysDestination = 'Royaume-uni'
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
			else 
				begin
					update tariffExport
					set tariff_rate = 1
					where refPr = @refPr 
				end
		end
end