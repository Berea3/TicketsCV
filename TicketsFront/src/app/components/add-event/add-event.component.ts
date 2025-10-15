import { Component } from '@angular/core';
import {HeaderComponent} from "../header/header.component";
import {FormsModule} from "@angular/forms";
import { HttpClient } from "@angular/common/http";
import {Theater} from "../../entities/Theater";
import {LinkService} from "../../services/link.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-add-event',
  standalone: true,
    imports: [
        FormsModule,
        RouterLink,
        HeaderComponent
    ],
  templateUrl: './add-event.component.html',
  styleUrl: './add-event.component.css'
})
export class AddEventComponent {
}
