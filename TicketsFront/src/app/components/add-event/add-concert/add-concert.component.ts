import { Component } from '@angular/core';
import {Concert} from '../../../entities/Concert';
import {Seating} from '../../../entities/layout/Seating';
import {HttpClient} from '@angular/common/http';
import {LinkService} from '../../../services/link.service';
import {BearInputTextComponent} from 'bear-library';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HeaderComponent} from '../../header/header.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-concert',
    imports: [
        BearInputTextComponent,
        FormsModule,
        HeaderComponent,
        ReactiveFormsModule
    ],
  templateUrl: './add-concert.component.html',
  styleUrl: './add-concert.component.css'
})
export class AddConcertComponent {

    concert: Concert=new Concert();
    newFile: File;
    seatingId: string;

    constructor(private http: HttpClient, private link: LinkService, private router: Router) {}

    ngOnInit()
    {
    }

    onFileAdded(event: any)
    {
        this.newFile=event.target.files[0];
    }

    onSubmit()
    {
        this.http.post(this.link.url+"/concerts/create",this.concert).subscribe(
            (response: any)=>{
                if (response.id==null) console.log("null id");
                else
                {
                    const formData=new FormData();
                    formData.append('file',this.newFile);
                    this.http.post(this.link.url+"/concerts/setFile/"+response.id,formData).subscribe(
                        (response: any)=>{
                            this.router.navigateByUrl("/manage");
                        }
                    );
                }
            }
        )
    }

    setName(name: string)
    {
        this.concert.name=name;
    }

    setDescription(description: string)
    {
        this.concert.description=description;
    }
}
